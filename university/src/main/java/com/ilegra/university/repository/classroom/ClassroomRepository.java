package com.ilegra.university.repository.classroom;

import com.ilegra.university.model.Classroom;

import java.util.List;
import java.util.Optional;

public interface ClassroomRepository {

    void save(Classroom classroom);
    void update(Classroom classroom);
    Optional<Classroom> findById(int id);
    Optional<Classroom> findByName(String name);
    List<Classroom> findAll();
}
