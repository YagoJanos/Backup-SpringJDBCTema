package com.ilegra.university.repository.program;

import com.ilegra.university.exception.DBException;
import com.ilegra.university.model.Program;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Repository
public class ProgramRepositoryJDBC implements ProgramRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Program> programRowMapper = (rs, rowNum) -> Program.newBuilder()
            .withId(rs.getInt("id"))
            .withName(rs.getString("name"))
            .withDegreeId(rs.getInt("degree_id"))
            .withTotalSemesters(rs.getShort("total_semesters"))
            .withActive(rs.getBoolean("active"))
            .withVersion(rs.getInt("version"))
            .build();



    public ProgramRepositoryJDBC(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }



    @Override
    public int save(Program program) {

        String sql = "INSERT INTO programs (name, degree_id, total_semesters, active, version) VALUES (?, ?, ?, true, 1)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                        ps.setString(1, program.getName());
                        ps.setInt(2, program.getDegreeId());
                        ps.setShort(3, program.getTotalSemesters());
                        return ps;
                    },
                    keyHolder
            );

            int id = keyHolder.getKey().intValue();

            saveModificationHistory(program, id, true, 1);

            return id;

        } catch (DataAccessException e){
            throw new DBException("An error occurred while trying to save program and retrieve generated id");
        }
    }

    @Override
    public void update(Program program, int newVersion) {
        String sql = "UPDATE programs SET name = ?, total_semesters = ?, version = ? WHERE id = ? and active = true";

        try {
            int id = program.getId();
            jdbcTemplate.update(sql, program.getName(), program.getTotalSemesters(), newVersion, id);

            saveModificationHistory(program, id, true, newVersion);
        } catch (DataAccessException e) {
            throw new DBException("An error occurred while trying to update the program that has id " + program.getId(), e);
        }
    }

    public void softDelete(int id, int newVersion) {
        String sql = "UPDATE programs SET active = false, version = ? WHERE active = true AND id = ?";
        try {
            jdbcTemplate.update(sql, id);
            findById(id).ifPresent(program -> saveModificationHistory(program, id, false, newVersion));
        } catch (DataAccessException e) {
            throw new DBException("An error occurred while trying to delete program with id " + id, e);
        }
    }

    private void saveModificationHistory(Program program, int id, boolean active, int version) {
        String sqlSaveHistory = "INSERT INTO programs_history (id, name, degree_id, total_semesters, active, version) VALUES ( ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlSaveHistory, id, program.getName(), program.getDegreeId(), program.getTotalSemesters(), active, version);
    }



    @Override
    public Optional<Program> findById(int id){
        String sql = "SELECT id, name, degree_id, total_semesters, active, version FROM programs WHERE active = true AND id = ?";
        try {
            return jdbcTemplate.query(sql, programRowMapper, id).stream().findFirst();
        } catch (DataAccessException e){
            throw new DBException("An error occurred while trying to find programs with " + id, e);
        }
    }

    @Override
    public List<Program> findByName(String name){
        String sql = "SELECT id, name, degree_id, total_semesters, active, version FROM programs WHERE active = true and name = ?";
        try {
            return Collections.unmodifiableList(jdbcTemplate.query(sql, programRowMapper, name));
        } catch (DataAccessException e){
            throw new DBException("An error occurred while trying to find programs with name " + name, e);
        }
    }

    @Override
    public Optional<Program> findByNameAndDegree(String name, int degreeId) {
        String sql = "SELECT id, name, degree_id, total_semesters, active, version FROM programs WHERE active = true AND name = ? AND degree_id = ?";
        try {
            return jdbcTemplate.query(sql, programRowMapper, name, degreeId).stream().findFirst();
        } catch (DataAccessException e){
            throw new DBException("An error occurred while trying to find programs with name " + name + " and degree id" + degreeId, e);
        }
    }


    public List<Program> findAll() {
        String sql = "SELECT id, name, degree_id, total_semesters, active, version FROM programs WHERE active = true";
        try {
            return jdbcTemplate.query(sql, programRowMapper);
        } catch (DataAccessException e){
            throw new DBException("An error occurred while trying to find programs", e);
        }
    }




    @Override
    public void removeAllSubjectsFromProgram(int programEntityId) {
        String sql = "DELETE FROM programs_subjects WHERE program_id = ?";

        try {
            jdbcTemplate.update(sql, programEntityId);
        } catch (DataAccessException e) {
            throw new DBException("An error occurred while trying to remove subject with entity id" + programEntityId + " from all programs");
        }
    }
}

