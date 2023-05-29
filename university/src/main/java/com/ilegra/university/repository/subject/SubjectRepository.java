package com.ilegra.university.repository.subject;

import com.ilegra.university.model.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository {
    int save(Subject subject);
    void update(Subject subject, int version);
    void softDelete(int id, int version);
    Optional<Subject> findById(int id);
    Optional<Subject> findByName(String name);
    List<Subject> findAll();



    List<Integer> getAllProgramsRelatedToThisSubject(int subjectId);
    void addSubjectToProgram(int subjectId, int programId);
    void removeSubjectFromAllPrograms(int subjectId);
}
