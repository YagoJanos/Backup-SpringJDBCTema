package com.ilegra.university.repository.classroom;

import com.ilegra.university.exception.DBException;
import com.ilegra.university.model.Classroom;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class ClassroomRepositoryJDBC implements ClassroomRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Classroom> classroomRowMapper = (rs, rowNum) -> Classroom.newBuilder()
            .withId(rs.getInt("id"))
            .withName(rs.getString("name"))
            .build();



    public ClassroomRepositoryJDBC(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }



    public void save(Classroom classroom) {
        String sql = "INSERT INTO classrooms (name) VALUES (?)";
        try {
            jdbcTemplate.update(sql, classroom.getName());
        } catch (DataAccessException e){
            throw new DBException("An error occurred while trying to save classroom");
        }
    }

    public void update(Classroom classroom) {
        String sql = "UPDATE classrooms SET name = ? WHERE id = ?";
        try {
            jdbcTemplate.update(sql, classroom.getName(), classroom.getId());
        } catch (DataAccessException e) {
            throw new DBException("An error occurred while trying to update the classroom with id " + classroom.getId(), e);
        }
    }



    public Optional<Classroom> findById(int id) {
        String sql = "SELECT id, name FROM classrooms WHERE id = ?";
        try {
            return jdbcTemplate.query(sql, classroomRowMapper, id).stream().findFirst();
        } catch (DataAccessException e){
            throw new DBException("An error occurred while trying to find the classroom with id " + id, e);
        }
    }

    public Optional<Classroom> findByName(String name) {
        String sql = "SELECT id, name FROM classrooms WHERE name = ?";
        try {
            return jdbcTemplate.query(sql, classroomRowMapper, name).stream().findFirst();
        } catch (DataAccessException e){
            throw new DBException("An error occurred while trying to find classrooms with name " + name, e);
        }
    }

    public List<Classroom> findAll() {
        String sql = "SELECT id, name FROM classrooms";
        try {
            return Collections.unmodifiableList(jdbcTemplate.query(sql, classroomRowMapper));
        } catch (DataAccessException e){
            throw new DBException("An error occurred while trying to find classrooms", e);
        }
    }
}

