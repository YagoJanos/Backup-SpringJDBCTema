package com.ilegra.university.repository.enrollment;

import com.ilegra.university.model.ProgramEnrollment;
import com.ilegra.university.model.SubjectEnrollment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EnrollmentRepository {

    void enrollStudentInSubject(int studentId, int subjectId);
    void dropStudentFromSubject(int studentId, int subjectId);



    void enrollStudentInProgram(int studentId, int programId);
    void dropStudentFromProgram(int studentId, int subjectId);



    void assignScore(BigDecimal score, int studentId, int subjectEntityId);



    Optional<SubjectEnrollment> findActiveSubjectEnrollment (int studentId, int subjectEntityId);
    Optional<ProgramEnrollment> findActiveProgramEnrollment(int studentId, int programEntityId);
    List<SubjectEnrollment> findAllSubjectsEnrolledByStudent(int studentId);
    List<ProgramEnrollment> findAllProgramsEnrolledByStudent(int studentId);



    int getNumberOfStudentsEnrolledInSubjectNow(int subjectEntityId);
    int getNumberOfStudentsEnrolledInProgramNow(int programEntityId);



    Map<Integer, BigDecimal> findAllStudentScoresForSubject(int subjectEntityId);
}
