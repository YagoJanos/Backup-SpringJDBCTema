package com.ilegra.university.service;

import com.ilegra.university.exception.EntityAlreadyExistsException;
import com.ilegra.university.exception.OperationConflictException;
import com.ilegra.university.exception.ResourceNotFoundException;
import com.ilegra.university.model.Subject;
import com.ilegra.university.repository.subject.SubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final ProgramService programService;
    private final EnrollmentService enrollmentService;

    public SubjectService(SubjectRepository subjectRepository, ProgramService programService, EnrollmentService enrollmentService){
        this.subjectRepository = subjectRepository;
        this.programService = programService;
        this.enrollmentService = enrollmentService;
    }




    @Transactional
    public void registerSubject(Subject subject, List<Integer> programIdList){

        ensureSubjectIsUnique(subject.getName());

        ensureAllProgramsExist(programIdList);

        addSubjectToPrograms(subjectRepository.save(subject), programIdList);
    }

    @Transactional
    public void updateSubject(Subject subject, List<Integer> programIdList) {

        int id = subject.getId();

        int newVersion = ensureSubjectExist(id).getVersion() + 1;

        ensureNoOtherSubjectWithThisNameExist(subject.getName(), id);

        ensureAllProgramsExist(programIdList);

        subjectRepository.update(subject, newVersion);

        updateSubjectProgramRelations(id, programIdList);
    }

    @Transactional
    public void deleteSubject(int id) {

        if(enrollmentService.getNumberOfStudentsEnrolledInSubjectNow(id) != 0){
            throw new OperationConflictException("Subject with id id " + id + " still has active enrollments and cannot be deleted.");
        }

        removeOldSubjectFromAllPrograms(id);
        subjectRepository.softDelete(id, ensureSubjectExist(id).getVersion() + 1);
    }

    public Optional<Subject> findById(int id){
        return subjectRepository.findById(id);
    }

    public Optional<Subject> findByName(String name) {
        return subjectRepository.findByName(name);
    }

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }




    List<Integer> getAllProgramsRelatedToThisSubject(int subjectId){
        return subjectRepository.getAllProgramsRelatedToThisSubject(subjectId);
    }
    private void updateSubjectProgramRelations(int id, List<Integer> programIdList) {
        removeOldSubjectFromAllPrograms(id);
        addSubjectToPrograms(id, programIdList);
    }

    private void addSubjectToPrograms(int subjectId, List<Integer> programIdList) {
        for (Integer programId : programIdList) {
            subjectRepository.addSubjectToProgram(subjectId, programId);
        }
    }

    private void removeOldSubjectFromAllPrograms(int subjectId){
        subjectRepository.removeSubjectFromAllPrograms(subjectId);
    }




    public Subject ensureSubjectExist(int id) {
        return findById(id).orElseThrow(() -> new ResourceNotFoundException("A subject with id " + id + " does not exist"));
    }

    private void ensureSubjectIsUnique(String name){
        findByName(name).ifPresent(subject -> {throw new EntityAlreadyExistsException("A subject with name " + name + " already exists");});
    }

    private void ensureNoOtherSubjectWithThisNameExist(String name, int id) {
        Optional<Subject> subjectOptional = findByName(name);
        if(subjectOptional.isPresent() && subjectOptional.get().getId() != id){
            throw new EntityAlreadyExistsException("A subject with name " + name + " already exists with a different id");
        }
    }

    private void ensureAllProgramsExist(List<Integer> programIdList) {
        List<Integer> invalidProgramIds = new ArrayList<>();
        for(int programId : programIdList){
            programService.findById(programId).ifPresentOrElse(value -> {}, () -> invalidProgramIds.add(programId));
        }
        invalidProgramIds.stream()
            .findAny()
            .ifPresent(id -> {throw new ResourceNotFoundException("These programs ids do not exist: " + invalidProgramIds);});
    }
}
