package com.ilegra.university.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SubjectEnrollment implements Serializable {

    private static final Long serialVersionUID = 1L;

    private final Integer enrollmentId;
    private final Integer studentId;
    private final Integer subjectId;
    private final BigDecimal score;
    private final LocalDateTime enrollTime;
    private final LocalDateTime dropTime;
    private final Boolean active;

    private SubjectEnrollment(Builder builder){
        this.enrollmentId = builder.enrollmentId;
        this.studentId = builder.studentId;
        this.subjectId = builder.subjectId;
        this.score = builder.score;
        this.enrollTime = builder.enrollTime;
        this.dropTime = builder.dropTime;
        this.active = builder.active;
    }

    public Integer getEnrollmentId() {
        return enrollmentId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public BigDecimal getScore() {
        return score;
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
        private Integer subjectId;
        private BigDecimal score;
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

        public Builder withSubjectId(Integer subjectId){
            this.subjectId = subjectId;
            return this;
        }

        public Builder withScore(BigDecimal score){
            this.score = score;
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

        public SubjectEnrollment build(){
            return new SubjectEnrollment(this);
        }
    }
}
