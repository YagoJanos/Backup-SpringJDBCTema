package com.ilegra.university.repository.program;

import com.ilegra.university.model.Program;

import java.util.List;
import java.util.Optional;

public interface ProgramRepository {
    int save(Program program);
    void update(Program program, int version);
    void softDelete(int id, int version);
    List<Program> findByName(String name);
    Optional<Program> findById(int id);
    Optional<Program> findByNameAndDegree(String name, int degreeId);
    List<Program> findAll();

    void removeAllSubjectsFromProgram(int programEntityId);
}
