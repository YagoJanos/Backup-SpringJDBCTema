package com.ilegra.university.repository.student;

import com.ilegra.university.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    void save(Student student);
    void update(Student student);
    List<Student> findByName(String name, int pageSize, int page);
    Optional<Student> findById(int id);
    List<Student> findAll(int pageSize, int page);
}
