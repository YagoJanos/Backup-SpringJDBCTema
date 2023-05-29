package com.ilegra.university.repository.professor;

import com.ilegra.university.model.Professor;

import java.util.List;
import java.util.Optional;
public interface ProfessorRepository {
    void save(Professor professor);
    void update(Professor professor);
    Optional<Professor> findById(int id);
    List<Professor> findByName(String name, int pageSize, int page);
    List<Professor> findAll(int pageSize, int page);
}
