package com.ilegra.university.controller;

import com.ilegra.university.dto.output.ProgramEnrollmentOutputDTO;
import com.ilegra.university.dto.output.SubjectEnrollmentOutputDTO;
import com.ilegra.university.mapper.ProgramEnrollmentMapper;
import com.ilegra.university.mapper.SubjectEnrollmentMapper;
import com.ilegra.university.service.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }



    @GetMapping("/api/v1/students/{studentId}/subjects")
    @ResponseStatus(HttpStatus.OK)
    public List<SubjectEnrollmentOutputDTO> getAllSubjectsEnrolledByStudent(@PathVariable int studentId) {
        return enrollmentService.findAllSubjectsEnrolledByStudent(studentId).stream().map(SubjectEnrollmentMapper::convertToDTO).collect(Collectors.toUnmodifiableList());
    }

    @GetMapping("/api/v1/students/{studentId}/programs")
    @ResponseStatus(HttpStatus.OK)
    public List<ProgramEnrollmentOutputDTO> getAllProgramsEnrolledByStudent(@PathVariable int studentId) {
        return enrollmentService.findAllProgramsEnrolledByStudent(studentId).stream().map(ProgramEnrollmentMapper::convertToDTO).collect(Collectors.toUnmodifiableList());
    }

    @GetMapping("/api/v1/subjects/score/{subjectEntityId}")
    @ResponseStatus(HttpStatus.OK)
    public Map<Integer, BigDecimal> getScoreOfAllStudentsForSubject(@PathVariable int subjectEntityId) {
        return enrollmentService.findAllStudentScoresForSubject(subjectEntityId);
    }



    @PostMapping("/api/v1/students/{studentId}/subject/{subjectId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void enrollStudentInSubject(@PathVariable int studentId, @PathVariable int subjectId) {
        enrollmentService.enrollStudentInSubject(studentId, subjectId);
    }

    @PostMapping("/api/v1/students/{studentId}/program/{programId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void enrollStudentInProgram(@PathVariable int studentId, @PathVariable int programId) {
        enrollmentService.enrollStudentInProgram(studentId, programId);
    }

    @PatchMapping("/api/v1/students/score/{studentId}/subject/{subjectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void assignScore(@PathVariable int studentId, @PathVariable int subjectId, @RequestBody BigDecimal score) {
        enrollmentService.assignScore(studentId, subjectId, score);
    }



    @DeleteMapping("/api/v1/students/{studentId}/subject/{subjectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void dropStudentFromSubject(@PathVariable int studentId, @PathVariable int subjectId) {
        enrollmentService.dropStudentFromSubject(studentId, subjectId);
    }

    @DeleteMapping("/api/v1/students/{studentId}/program/{programId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void dropStudentFromProgram(@PathVariable int studentId, @PathVariable int programId) {
        enrollmentService.dropStudentFromProgram(studentId, programId);
    }
}

