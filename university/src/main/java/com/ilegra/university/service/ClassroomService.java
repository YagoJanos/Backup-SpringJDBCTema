package com.ilegra.university.service;

import com.ilegra.university.exception.EntityAlreadyExistsException;
import com.ilegra.university.model.Classroom;
import com.ilegra.university.repository.classroom.ClassroomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassroomService {
    private final ClassroomRepository classroomRepository;

    public ClassroomService(ClassroomRepository classroomRepository){
        this.classroomRepository = classroomRepository;
    }


    public void registerClassroom(Classroom classroom){
        ensureClassroomIsUnique(classroom.getName());
        classroomRepository.save(classroom);
    }

    public void updateClassroom(Classroom classroom) {
        ensureNoOtherClassroomWithThisNameExist(classroom.getName(), classroom.getId());
        classroomRepository.update(classroom);
    }



    public Optional<Classroom> findClassroomById(int id){

        return classroomRepository.findById(id);
    }

    public Optional<Classroom> findClassroomByName(String name) {

        return classroomRepository.findByName(name);
    }

    public List<Classroom> findAllClassrooms() {

        return classroomRepository.findAll();
    }



    private void ensureClassroomIsUnique(String name){
        findClassroomByName(name).ifPresent(classroom -> {throw new EntityAlreadyExistsException("A classroom with name " + name + " already exists");});
    }

    private void ensureNoOtherClassroomWithThisNameExist(String name, int id) {
        Optional<Classroom> classroomOptional = findClassroomByName(name);
        if(classroomOptional.isPresent() && classroomOptional.get().getId() != id){
            throw new EntityAlreadyExistsException("A classroom with name " + name + " already exists with a different id");
        }
    }
}

