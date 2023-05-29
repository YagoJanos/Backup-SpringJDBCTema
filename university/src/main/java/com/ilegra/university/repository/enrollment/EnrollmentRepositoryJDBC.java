package com.ilegra.university.repository.enrollment;

import com.ilegra.university.exception.DBException;
import com.ilegra.university.model.ProgramEnrollment;
import com.ilegra.university.model.SubjectEnrollment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.*;

@Repository
public class EnrollmentRepositoryJDBC implements EnrollmentRepository {
    private final JdbcTemplate jdbcTemplate;

    public EnrollmentRepositoryJDBC(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final RowMapper<SubjectEnrollment> subjectEnrollmentRowMapper = (rs, rowNum) -> SubjectEnrollment.newBuilder()
            .withEnrollmentId(rs.getInt("enrollment_id"))
            .withStudentId(rs.getInt("student_id"))
            .withSubjectId(rs.getInt("subject_id"))
            .withScore(rs.getBigDecimal("score"))
            .withEnrollTime(rs.getTimestamp("enroll_time").toLocalDateTime())
            .withDropTime(rs.getTimestamp("drop_time").toLocalDateTime())
            .withActive(rs.getBoolean("active"))
            .build();

    private final RowMapper<ProgramEnrollment> programEnrollmentRowMapper = (rs, rowNum) -> ProgramEnrollment.newBuilder()
            .withEnrollmentId(rs.getInt("enrollment_id"))
            .withStudentId(rs.getInt("student_id"))
            .withProgramId(rs.getInt("program_id"))
            .withEnrollTime(rs.getTimestamp("enroll_time").toLocalDateTime())
            .withDropTime(rs.getTimestamp("drop_time").toLocalDateTime())
            .withActive(rs.getBoolean("active"))
            .build();



    @Override
    public void enrollStudentInSubject(int studentId, int subjectId) {
        String sql = "INSERT INTO subject_enrollment (student_id, subject_id, active) VALUES (?, ?, true)";
        try {
            jdbcTemplate.update(sql, studentId, subjectId);
        } catch (DataAccessException e) {
            throw new DBException("An error occurred while trying to enroll student with id " + studentId + " in subject with id" + subjectId, e);
        }
    }

    @Override
    public void enrollStudentInProgram(int studentId, int programId){
        String sql = "INSERT INTO program_enrollment (student_id, program_id, active) VALUES (?, ?, true)";
        try {
            jdbcTemplate.update(sql, studentId, programId);
        } catch (DataAccessException e) {
            throw new DBException("An error occurred while trying to enroll student with id " + studentId + " in program with id" + programId, e);
        }
    }




    @Override
    public void dropStudentFromSubject(int studentId, int subjectId) {
        String sql = "UPDATE subject_enrollment SET active = false, drop_time = NOW() WHERE active = true AND student_id = ? AND subject_id = ?)";
        try {
            jdbcTemplate.update(sql, studentId, subjectId);
        } catch (DataAccessException e) {
            throw new DBException("An error occurred while trying to drop student with id " + studentId + " from subject with id" + subjectId, e);
        }
    }

    @Override
    public void dropStudentFromProgram(int studentId, int programId) {
        String sql = "UPDATE program_enrollment SET active = false, drop_time = NOW() WHERE active = true AND student_id = ? AND program_id = ?)";
        try {
            jdbcTemplate.update(sql, studentId, programId);
        } catch (DataAccessException e) {
            throw new DBException("An error occurred while trying to drop student with id " + studentId + " from program with id" + programId, e);
        }
    }




    @Override
    public void assignScore(BigDecimal score, int studentId, int subjectId) {
        String sql = "UPDATE subject_enrollment SET score = ? WHERE student_id = ? AND subject_id = ? AND active = true";
        try {
            jdbcTemplate.update(sql, score, studentId, subjectId);
        } catch (DataAccessException e) {
            throw new DBException("An error occurred while trying to assign score to student with id" + studentId + " in subject with id" + subjectId, e);
        }
    }



    @Override
    public Optional<SubjectEnrollment> findActiveSubjectEnrollment (int studentId, int subjectId) {
        String sql = "SELECT enrollment_id, student_id, subject_id, score, enroll_time, drop_time, active FROM subject_enrollment WHERE student_id = ? AND subject_id = ? AND active = true";
        try {
            return jdbcTemplate.query(sql, subjectEnrollmentRowMapper, studentId, subjectId).stream().findFirst();
        } catch (DataAccessException e) {
            throw new DBException("An error occurred while trying to find subject enrollment");
        }
    }

    @Override
    public Optional<ProgramEnrollment> findActiveProgramEnrollment(int studentId, int programId) {
        String sql = "SELECT enrollment_id, student_id, program_id, enroll_time, drop_time, active FROM program_enrollment WHERE student_id = ? AND program_id = ? AND active = true";
        try {
            return jdbcTemplate.query(sql, programEnrollmentRowMapper, studentId, programId).stream().findFirst();
        } catch (DataAccessException e) {
            throw new DBException("An error occurred while trying to find program enrollment");
        }
    }

    @Override
    public int getNumberOfStudentsEnrolledInSubjectNow(int subjectId) {
        String sql = "SELECT COUNT(*) FROM subject_enrollment " +
                     "WHERE active = true AND subject_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, subjectId);
        } catch (DataAccessException e) {
            throw new DBException("An error occurred while trying to count the number of students enrolled in the subject with id " + subjectId, e);
        }
    }

    @Override
    public int getNumberOfStudentsEnrolledInProgramNow(int programId) {
        String sql = "SELECT COUNT(*) FROM program_enrollment " +
                     "WHERE active = true AND program_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, programId);
        } catch (DataAccessException e) {
            throw new DBException("An error occurred while trying to count the number of students enrolled in the program with id " + programId, e);
        }
    }

    @Override
    public List<SubjectEnrollment> findAllSubjectsEnrolledByStudent(int studentId) {
        String sql = "SELECT enrollment_id, student_id, subject_id, score, enroll_time, drop_time, active FROM subject_enrollment WHERE student_id = ? AND active = true";

        try {
            return Collections.unmodifiableList(jdbcTemplate.query(sql, subjectEnrollmentRowMapper, studentId));
        } catch (DataAccessException e) {
            throw new DBException("An error occurred while trying to find subjects for student with id " + studentId, e);
        }
    }

    @Override
    public List<ProgramEnrollment> findAllProgramsEnrolledByStudent(int studentId) {
        String sql = "SELECT enrollment_id, student_id, program_id, enroll_time, drop_time, active FROM program_enrollment WHERE student_id = ? AND active = true";
        try {
            return Collections.unmodifiableList(jdbcTemplate.query(sql, programEnrollmentRowMapper, studentId));
        } catch (DataAccessException e) {
            throw new DBException("An error occurred while trying to find programs for student with id " + studentId, e);
        }
    }

    @Override
    public Map<Integer, BigDecimal> findAllStudentScoresForSubject(int subjectId) {
        String sql = "SELECT student_id, score FROM subject_enrollment WHERE subject_id = ?";
        try {
            Map<Integer, BigDecimal> scores = jdbcTemplate.query(sql, (ResultSet rs) -> {
                HashMap<Integer, BigDecimal> map = new HashMap<>();
                while(rs.next()){
                    map.put(rs.getInt("student_id"), rs.getBigDecimal("score"));
                }
                return map;
            }, subjectId);
            return Collections.unmodifiableMap(scores);
        } catch (DataAccessException e) {
            throw new DBException("An error occurred while trying to get all student scores for subject with id" + subjectId, e);
        }
    }

}
