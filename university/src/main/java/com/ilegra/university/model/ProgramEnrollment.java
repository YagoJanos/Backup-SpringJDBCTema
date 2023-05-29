package com.ilegra.university.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ProgramEnrollment implements Serializable {

    private static final Long serialVersionUID = 1L;
    private final Integer enrollmentId;
    private final Integer studentId;
    private final Integer programId;
    private final LocalDateTime enrollTime;
    private final LocalDateTime dropTime;
    private final Boolean active;

    private ProgramEnrollment(Builder builder){
        this.enrollmentId = builder.enrollmentId;
        this.studentId = builder.studentId;
        this.programId = builder.programId;
        this.enrollTime = builder.enrollTime;
        this.dropTime = builder.dropTime;
        this.active = builder.active;
    }

    public Integer getEnrollmentId() {return enrollmentId;}

    public Integer getStudentId() {
        return studentId;
    }

    public Integer getProgramId() {
        return programId;
    }

    public LocalDateTime getEnrollTime() {
        return enrollTime;
    }

    public LocalDateTime getDropTime() {
        return dropTime;
    }

    public Boolean getActive() {
        return active;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private Integer enrollmentId;
        private Integer studentId;
        private Integer programId;
        private LocalDateTime enrollTime;
        private LocalDateTime dropTime;
        private Boolean active;

        private Builder(){}

        public Builder withEnrollmentId(Integer enrollmentId){
            this.enrollmentId = enrollmentId;
            return this;
        }

        public Builder withStudentId(Integer studentId){
            this.studentId = studentId;
            return this;
        }

        public Builder withProgramId(Integer programId){
            this.programId = programId;
            return this;
        }

        public Builder withEnrollTime(LocalDateTime enrollTime){
            this.enrollTime = enrollTime;
            return this;
        }
        public Builder withDropTime(LocalDateTime dropTime){
            this.dropTime = dropTime;
            return this;
        }
        public Builder withActive(Boolean active){
            this.active = active;
            return this;
        }

        public ProgramEnrollment build(){
            return new ProgramEnrollment(this);
        }
    }
}
