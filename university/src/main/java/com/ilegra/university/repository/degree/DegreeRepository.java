package com.ilegra.university.repository.degree;

import com.ilegra.university.model.Degree;

import java.util.List;
import java.util.Optional;

public interface DegreeRepository {
    void save(Degree degree);
    void update(Degree degree);
    Optional<Degree> findById(int id);
    Optional<Degree> findByName(String name);
    List<Degree> findAll();
}
