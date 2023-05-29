package com.ilegra.university.service;

import com.ilegra.university.exception.EntityAlreadyExistsException;
import com.ilegra.university.exception.OperationConflictException;
import com.ilegra.university.exception.ResourceNotFoundException;
import com.ilegra.university.model.Degree;
import com.ilegra.university.model.Program;
import com.ilegra.university.repository.program.ProgramRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramService {

    private final ProgramRepository programRepository;
    private final DegreeService degreeService;
    private final EnrollmentService enrollmentService;

    public ProgramService(ProgramRepository programRepository, DegreeService degreeService, EnrollmentService enrollmentService){
        this.programRepository = programRepository;
        this.degreeService = degreeService;
        this.enrollmentService = enrollmentService;
    }


    @Transactional
    public void registerProgram(Program program){

        ensureProgramIsUnique(program.getName(), program.getDegreeId());
        ensureDegreeExist(program.getDegreeId());

        programRepository.save(program);
    }

    @Transactional
    public void updateProgram(Program program) {

        int id = program.getId();

        int newVersion = ensureProgramExist(id).getVersion() + 1;

        ensureDegreeExist(program.getDegreeId());

        ensureNoOtherProgramWithThisNameAndDegreeExist(program.getName(), program.getDegreeId(), id);

        programRepository.update(program, newVersion);
    }

    @Transactional
    public void deleteProgram(int id) {
        if(enrollmentService.getNumberOfStudentsEnrolledInProgramNow(id) != 0){
            throw new OperationConflictException("Program with entity id " + id + " still has active enrollments and cannot be deleted.");
        }

        removeAllSubjectsFromProgram(id);
        programRepository.softDelete(id, ensureProgramExist(id).getVersion() + 1);
    }

    public Optional<Program> findById(int id){
        return programRepository.findById(id);
    }

    public List<Program> findByName(String name) {
        return programRepository.findByName(name);
    }

    public Optional<Program> findByNameAndDegree(String name, Integer degreeId) {
        return programRepository.findByNameAndDegree(name, degreeId);
    }

    public List<Program> findAll() {
        return programRepository.findAll();
    }




    public void removeAllSubjectsFromProgram(int programId){
        programRepository.removeAllSubjectsFromProgram(programId);
    }




    private Program ensureProgramExist(int id) {
        return findById(id).orElseThrow(() -> new ResourceNotFoundException("An program with id " + id + " does not exist"));
    }

    private void ensureProgramIsUnique(String name, int degreeId) {
        programRepository.findByNameAndDegree(name, degreeId).ifPresent(program -> {throw new EntityAlreadyExistsException("An active program with name " + name + " in degree with id " + degreeId + " already exists");});
    }

    private void ensureNoOtherProgramWithThisNameAndDegreeExist(String name, Integer degreeId, Integer id) {
        Optional<Program> programOptional = findByNameAndDegree(name, degreeId);
        if(programOptional.isPresent() && programOptional.get().getId() != id){
            throw new EntityAlreadyExistsException("A program with name " + name + " and degree id " + degreeId + " already exists with a different id");
        }
    }

    public Degree ensureDegreeExist(int degreeId) {
        return degreeService.findDegreeById(degreeId).orElseThrow(() -> new ResourceNotFoundException("A degree with id " + degreeId + " does not exist"));
    }
}
