package com.ilegra.university.service;

import com.ilegra.university.exception.ResourceNotFoundException;
import com.ilegra.university.model.ProgramEnrollment;
import com.ilegra.university.model.SubjectEnrollment;
import com.ilegra.university.repository.enrollment.EnrollmentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentService studentService;
    private final SubjectService subjectService;
    private final ProgramService programService;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, StudentService studentService, SubjectService subjectService, ProgramService programService){
        this.enrollmentRepository = enrollmentRepository;
        this.studentService = studentService;
        this.subjectService = subjectService;
        this.programService = programService;
    }




    public void enrollStudentInSubject(int studentId, int subjectId) {
        ensureStudentExist(studentId);
        ensureSubjectExist(subjectId);
        enrollmentRepository.enrollStudentInSubject(studentId, subjectId);
    }

    public void enrollStudentInProgram(int studentId, int programId) {
        ensureStudentExist(studentId);
        ensureProgramExist(programId);
        enrollmentRepository.enrollStudentInProgram(studentId, programId);
    }




    public void dropStudentFromSubject(int studentId, int subjectId){
        ensureStudentExist(studentId);
        ensureSubjectExist(subjectId);
        enrollmentRepository.dropStudentFromSubject(studentId, subjectId);
    }
    public void dropStudentFromProgram(int studentId, int programId){
        ensureStudentExist(studentId);
        ensureProgramExist(programId);
        enrollmentRepository.dropStudentFromProgram(studentId, programId);
    }




    public void assignScore(int studentId, int subjectId, BigDecimal score) {
        ensureStudentExist(studentId);
        ensureSubjectExist(subjectId);
        ensureSubjectEnrollmentExist(studentId, subjectId);
        enrollmentRepository.assignScore(score, studentId, subjectId);
    }


    public Optional<SubjectEnrollment> findActiveSubjectEnrollment (int studentId, int subjectEntityId){
        return enrollmentRepository.findActiveSubjectEnrollment(studentId, subjectEntityId);
    }

    public Optional<ProgramEnrollment> findActiveProgramEnrollment (int studentId, int programEntityId) {
        return enrollmentRepository.findActiveProgramEnrollment(studentId, programEntityId);
    }

    public List<SubjectEnrollment> findAllSubjectsEnrolledByStudent(int studentId) {
        return enrollmentRepository.findAllSubjectsEnrolledByStudent(studentId);
    }

    public List<ProgramEnrollment> findAllProgramsEnrolledByStudent(int studentId) {
        return enrollmentRepository.findAllProgramsEnrolledByStudent(studentId);
    }

    public Map<Integer, BigDecimal> findAllStudentScoresForSubject(int subjectEntityId) {
        ensureSubjectExist(subjectEntityId);
        return enrollmentRepository.findAllStudentScoresForSubject(subjectEntityId);
    }




    private void ensureSubjectEnrollmentExist(int studentId, int subjectId) {
        findActiveSubjectEnrollment(studentId, subjectId).orElseThrow(() -> new ResourceNotFoundException("Active enrollment with student id " + studentId + " and subject id " + subjectId + " does not exist"));
    }

    public void ensureStudentExist(int studentId){
        studentService.findStudentById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student with id " + studentId + " not found"));
    }

    public void ensureSubjectExist(int subjectId){
        subjectService.findById(subjectId).orElseThrow(() -> new ResourceNotFoundException("Subject with id " + subjectId + " not found"));
    }

    public void ensureProgramExist(int programId){
        programService.findById(programId).orElseThrow(() -> new ResourceNotFoundException("Program with id " + programId + " not found"));
    }



    int getNumberOfStudentsEnrolledInSubjectNow(int subjectId){
        return enrollmentRepository.getNumberOfStudentsEnrolledInSubjectNow(subjectId);
    }

    int getNumberOfStudentsEnrolledInProgramNow(int programId){
        return enrollmentRepository.getNumberOfStudentsEnrolledInProgramNow(programId);
    }
}
