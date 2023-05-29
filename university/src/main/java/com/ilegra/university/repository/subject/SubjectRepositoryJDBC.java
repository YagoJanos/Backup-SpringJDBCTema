package com.ilegra.university.repository.subject;

import com.ilegra.university.exception.DBException;
import com.ilegra.university.model.Subject;
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
public class SubjectRepositoryJDBC implements SubjectRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Subject> subjectRowMapper = (rs, rowNum) -> Subject.newBuilder()
            .withId(rs.getInt("id"))
            .withName(rs.getString("name"))
            .withHours(rs.getInt("total_hours"))
            .withClassRoomId(rs.getInt("classroom_id"))
            .withProfessorId(rs.getInt("professor_id"))
            .withActive(rs.getBoolean("active"))
            .withVersion(rs.getInt("version"))
            .build();



    public SubjectRepositoryJDBC(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }




    @Override
    public int save(Subject subject) {
        String sql = "INSERT INTO subjects (name, total_hours, active, version) VALUES (?, ?, true, 1)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcTemplate.update(
                    connection -> {
                        PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                        ps.setString(1, subject.getName());
                        ps.setInt(2, subject.getHours());
                        return ps;
                    },
                    keyHolder
            );

            int id = keyHolder.getKey().intValue();

            saveModificationHistory(subject, id, true,1);

            return id;

        } catch (DataAccessException e){
            throw new DBException("An error occurred while trying to save subject and retrieve generated id");
        }
    }

    @Override
    public void update(Subject subject, int version) {
        String sql = "UPDATE subjects SET name = ?, total_hours = ?, version = ? WHERE id = ? and active = true";

        try {
            jdbcTemplate.update(sql, subject.getName(), subject.getHours(), version, subject.getId());
            saveModificationHistory(subject, subject.getId(), true, version);
        } catch (DataAccessException e) {
            throw new DBException("An error occurred while trying to update the subject that has id " + subject.getId(), e);
        }
    }

    //fazer código para cadastrar professor e sala
    @Override
    public void softDelete(int id, int version) {
        String sql = "UPDATE subjects SET active = false, version = ? WHERE active = true AND id = ?";
        try {
            jdbcTemplate.update(sql, id, version);
            findById(id).ifPresent(subject -> saveModificationHistory(subject, id, false, version));
        } catch (DataAccessException e) {
            throw new DBException("An error occurred while trying to delete subject with id " + id, e);
        }

    }


    private void saveModificationHistory(Subject subject, int id, boolean active, int version) {
        String sqlSaveHistory = "INSERT INTO subjects_history (id, name, total_hours, active, version) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlSaveHistory, id, subject.getName(), subject.getHours(), active, version);
    }



    @Override
    public Optional<Subject> findById(int id){
        String sql = "SELECT id, name, total_hours, classroom_id, professor_id, active, version FROM subjects WHERE id = ? AND active = true";
        try {
            return jdbcTemplate.query(sql, subjectRowMapper, id).stream().findFirst();
        } catch (DataAccessException e){
            throw new DBException("An error occurred while trying to find subjects with id " + id, e);
        }
    }

    @Override
    public Optional<Subject> findByName(String name) {
        String sql = "SELECT id, name, total_hours, classroom_id, professor_id, active, version FROM subjects WHERE name = ? AND active = true";
        try {
            return jdbcTemplate.query(sql, subjectRowMapper, name).stream().findFirst();
        } catch (DataAccessException e){
            throw new DBException("An error occurred while trying to find subject with name " + name, e);
        }
    }

//    public Optional<Subject> findByNameAndId(String name, int id){
//        String sql = "SELECT * FROM subjects WHERE active = TRUE AND name = ? AND id = ?";
//        try {
//            return jdbcTemplate.query(sql, subjectRowMapper, name, id).stream().findFirst();
//        } catch (DataAccessException e){
//            throw new DBException("An error occurred while trying to find subjects with name " + name + " and id " + id, e);
//        }
//    }

    @Override
    public List<Subject> findAll() {
        String sql = "SELECT id, name, total_hours, classroom_id, professor_id, active, version FROM subjects WHERE active = true";
        try {
            return Collections.unmodifiableList(jdbcTemplate.query(sql, subjectRowMapper));
        } catch (DataAccessException e){
            throw new DBException("An error occurred while trying to find all subjects", e);
        }
    }


    @Override
    public List<Integer> getAllProgramsRelatedToThisSubject(int subjectId){
        String sql = "SELECT program_id FROM programs_subjects " +
                     "WHERE subject_id = ?";

        try {
            return Collections.unmodifiableList(jdbcTemplate.queryForList(sql, Integer.class, subjectId));
        } catch (DataAccessException e) {
            throw new DBException("An error occurred while trying to retrieve program ids for subject with id " + subjectId, e);
        }
    }

    @Override
    public void addSubjectToProgram(int subjectId, int programId){
        String sql = "INSERT INTO programs_subjects (subject_id, program_id) VALUES (?, ?)";

        try {
            jdbcTemplate.update(sql, subjectId, programId);
        } catch (DataAccessException e) {
            throw new DBException("An error occurred while trying to register subject with id" + subjectId + " in program with id" + programId, e);
        }
    }

    @Override
    public void removeSubjectFromAllPrograms(int subjectId) {
        String sql = "DELETE FROM programs_subjects WHERE subject_id = ?";

        try {
            jdbcTemplate.update(sql, subjectId);
        } catch (DataAccessException e) {
            throw new DBException("An error occurred while trying to remove subject with id " + subjectId + " from all programs");
        }
    }
}
