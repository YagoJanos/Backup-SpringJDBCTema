package com.ilegra.university.repository.student;

import com.ilegra.university.exception.DBException;
import com.ilegra.university.model.Student;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Repository
public class StudentRepositoryJDBC implements StudentRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Student> studentRowMapper = (rs, rowNum) -> Student.newBuilder()
            .withId(rs.getInt("id"))
            .withName(rs.getString("name"))
            .withEmail(rs.getString("email"))
            .withPhone(rs.getString("phone"))
            .withCpf(rs.getString("cpf"))
            .build();



    public StudentRepositoryJDBC(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }



    @Override
    public void save(Student student) {
        String sql = "INSERT INTO students (name, email, phone, cpf) VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, student.getName(), student.getEmail(), student.getPhone(), student.getCpf());
        } catch (DataAccessException e){
            throw new DBException("An error occurred while trying to save student");
        }
    }

    @Override
    public void update(Student student) {
        String sql = "UPDATE students SET name = ?, email = ?, phone = ?, cpf = ? WHERE id = ?";
        try {
            jdbcTemplate.update(sql, student.getName(), student.getEmail(), student.getPhone(), student.getCpf(), student.getId());
        } catch (DataAccessException e) {
            throw new DBException("An error occurred while trying to update the student with id " + student.getId(), e);
        }
    }

    @Override
    public Optional<Student> findById(int id) {
        String sql = "SELECT id, name, email, phone, cpf FROM students WHERE id = ?";
        try {
            return jdbcTemplate.query(sql, studentRowMapper, id).stream().findFirst();
        } catch (DataAccessException e){
            throw new DBException("An error occurred while trying to find the student with id " + id, e);
        }
    }

    @Override
    public List<Student> findByName(String name, int pageSize, int page) {
        String sql = "SELECT id, name, email, phone, cpf FROM students WHERE name = ? ORDER BY name ASC LIMIT ? OFFSET ?";
        try {
            return Collections.unmodifiableList(jdbcTemplate.query(sql, studentRowMapper, name, pageSize, page * pageSize));
        } catch (DataAccessException e){
            throw new DBException("An error occurred while trying to find students with name " + name, e);
        }
    }

    @Override
    public List<Student> findAll(int pageSize, int page) {
        String sql = "SELECT id, name, email, phone, cpf FROM students LIMIT ? OFFSET ?";
        try {
            return Collections.unmodifiableList(jdbcTemplate.query(sql, studentRowMapper, pageSize, page * pageSize));
        } catch (DataAccessException e){
            throw new DBException("An error occurred while trying to find students", e);
        }
    }
}
