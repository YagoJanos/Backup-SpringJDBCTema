package com.ilegra.university.service;

import com.ilegra.university.model.Student;
import com.ilegra.university.repository.student.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }



    public void registerStudent(Student student){

        studentRepository.save(student);
    }

    public void updateStudent(Student student) {

        studentRepository.update(student);
    }



    public Optional<Student> findStudentById(int id){

        return studentRepository.findById(id);
    }

    public List<Student> findStudentsByName(String name, int pageSize, int page) {

        return studentRepository.findByName(name, pageSize, page);
    }

    public List<Student> findAllStudents(int pageSize, int page) {

        return studentRepository.findAll(pageSize, page);
    }
}
