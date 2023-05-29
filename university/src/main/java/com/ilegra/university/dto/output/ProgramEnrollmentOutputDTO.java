package com.ilegra.university.dto.output;

import java.time.LocalDateTime;

public class ProgramEnrollmentOutputDTO {

    private Integer enrollmentId;
    private Integer studentId;
    private Integer programId;
    private LocalDateTime enrollTime;
    private LocalDateTime dropTime;
    private Boolean active;

    public ProgramEnrollmentOutputDTO(Integer enrollmentId, Integer studentId, Integer programId, LocalDateTime enrollTime, LocalDateTime dropTime, Boolean active) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.programId = programId;
        this.enrollTime = enrollTime;
        this.dropTime = dropTime;
        this.active = active;
    }

    public Integer getEnrollmentId(){return enrollmentId;}
    public void setEnrollmentId(Integer enrollmentId){this.enrollmentId = enrollmentId;}
    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getProgramId() {
        return programId;
    }

    public void setProgramId(Integer programId) {
        this.programId = programId;
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
