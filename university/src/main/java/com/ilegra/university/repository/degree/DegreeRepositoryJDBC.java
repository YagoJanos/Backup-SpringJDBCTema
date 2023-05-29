package com.ilegra.university.repository.degree;

import com.ilegra.university.exception.DBException;
import com.ilegra.university.model.Degree;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class DegreeRepositoryJDBC implements DegreeRepository{
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Degree> degreeRowMapper = (rs, rowNum) -> Degree.newBuilder()
            .withId(rs.getInt("id"))
            .withName(rs.getString("name"))
            .build();



    public DegreeRepositoryJDBC(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }



    public void save(Degree degree) {
        String sql = "INSERT INTO degrees (name) VALUES (?)";
        try {
            jdbcTemplate.update(sql, degree.getName());
        } catch (DataAccessException e){
            throw new DBException("An error occurred while trying to save degree");
        }
    }

    public void update(Degree degree) {
        String sql = "UPDATE degrees SET name = ? WHERE id = ?";
        try {
            jdbcTemplate.update(sql, degree.getName(), degree.getId());
        } catch (DataAccessException e) {
            throw new DBException("An error occurred while trying to update the degree with id " + degree.getId(), e);
        }
    }



    public Optional<Degree> findById(int id) {
        String sql = "SELECT id, name FROM degrees WHERE id = ?";
        try {
            return jdbcTemplate.query(sql, degreeRowMapper, id).stream().findFirst();
        } catch (DataAccessException e){
            throw new DBException("An error occurred while trying to find the degree with id " + id, e);
        }
    }

    public Optional<Degree> findByName(String name) {
        String sql = "SELECT id, name FROM degrees WHERE name = ?";
        try {
            return jdbcTemplate.query(sql, degreeRowMapper, name).stream().findFirst();
        } catch (DataAccessException e){
            throw new DBException("An error occurred while trying to find degrees with name " + name, e);
        }
    }

    public List<Degree> findAll() {
        String sql = "SELECT id, name FROM degrees";
        try {
            return Collections.unmodifiableList(jdbcTemplate.query(sql, degreeRowMapper));
        } catch (DataAccessException e){
            throw new DBException("An error occurred while trying to find degrees", e);
        }
    }
}
