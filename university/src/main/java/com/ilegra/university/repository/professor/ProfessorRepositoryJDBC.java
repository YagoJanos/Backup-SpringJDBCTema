package com.ilegra.university.repository.professor;

import com.ilegra.university.exception.DBException;
import com.ilegra.university.model.Professor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Repository
public class ProfessorRepositoryJDBC implements ProfessorRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Professor> professorRowMapper = (rs, rowNum) -> Professor.newBuilder()
            .withId(rs.getInt("id"))
            .withName(rs.getString("name"))
            .withEmail(rs.getString("email"))
            .withPhone(rs.getString("phone"))
            .withCpf(rs.getString("cpf"))
            .build();



    public ProfessorRepositoryJDBC(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }



    @Override
    public void save(Professor professor) {
        String sql = "INSERT INTO professors (name, email, phone, cpf) VALUES (?, ?, ?, ?)";
        try{
            jdbcTemplate.update(sql, professor.getName(), professor.getEmail(), professor.getPhone(), professor.getCpf());
        } catch (DataAccessException e){
            throw new DBException("An error occurred while trying to save professor");
        }
    }

    @Override
    public void update(Professor professor) {
        String sql = "UPDATE professors SET name = ?, email = ?, phone = ?, cpf = ? WHERE id = ?";
        try {
            jdbcTemplate.update(sql, professor.getName(), professor.getEmail(), professor.getPhone(), professor.getCpf(), professor.getId());
        } catch (DataAccessException e) {
            throw new DBException("An error occurred while trying to update the professor with id " + professor.getId(), e);
        }
    }



    public Optional<Professor> findById(int id) {
        String sql = "SELECT * FROM professors WHERE id = ?";
        try {
            return jdbcTemplate.query(sql, professorRowMapper, id).stream().findFirst();
        } catch (DataAccessException e){
            throw new DBException("An error occurred while trying to find the professor with id " + id, e);
        }
    }

    public List<Professor> findByName(String name, int pageSize, int page) {
        String sql = "SELECT * FROM professors WHERE name = ? ORDER BY name ASC LIMIT ? OFFSET ?";
        try {
            return Collections.unmodifiableList(jdbcTemplate.query(sql, professorRowMapper, name, pageSize, page * pageSize));
        } catch (DataAccessException e){
            throw new DBException("An error occurred while trying to find professors with name " + name, e);
        }
    }

    public List<Professor> findAll(int pageSize, int page) {
        String sql = "SELECT * FROM professors LIMIT ? OFFSET ?";
        try {
            return Collections.unmodifiableList(jdbcTemplate.query(sql, professorRowMapper, pageSize, page * pageSize));
        } catch (DataAccessException e){
            throw new DBException("An error occurred while trying to find professors", e);
        }
    }
}