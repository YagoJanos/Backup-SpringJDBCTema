package com.ilegra.university.dto.output;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public class SubjectEnrollmentOutputDTO {

    private Integer enrollmentId;
    private Integer studentId;
    private Integer subjectId;
    private BigDecimal score;
    private LocalDateTime enrollTime;
    private LocalDateTime dropTime;
    private Boolean active;

    public SubjectEnrollmentOutputDTO(Integer enrollmentId, Integer studentId, Integer subjectId, BigDecimal score, LocalDateTime enrollTime, LocalDateTime dropTime, Boolean active) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.score = score;
        this.enrollTime = enrollTime;
        this.dropTime = dropTime;
        this.active = active;
    }

    public Integer getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(Integer enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public LocalDateTime getEnrollTime() {
        return enrollTime;
    }

    public void setEnrollTime(LocalDateTime enrollTime) {
        this.enrollTime = enrollTime;
    }

    public LocalDateTime getDropTime() {
        return dropTime;
    }

    public void setDropTime(LocalDateTime dropTime) {
        this.dropTime = dropTime;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
