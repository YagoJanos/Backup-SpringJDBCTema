package com.ilegra.university.model;

import java.io.Serializable;

public class Subject implements Serializable {

    private static final Long serialVersionUID = 1L;

    private final Integer id;
    private final String name;
    private final Integer hours;
    private final Integer classRoomId;
    private final Integer professorId;
    private final boolean active;
    private final Integer version;

    private Subject(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.hours = builder.hours;
        this.classRoomId = builder.classRoomId;
        this.professorId = builder.professorId;
        this.active = builder.active;
        this.version = builder.version;
    }



    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getHours() {
        return hours;
    }

    public Integer getClassRoomId() {
        return classRoomId;
    }

    public Integer getProfessorId() {
        return professorId;
    }

    public boolean isActive() {
        return active;
    }

    public Integer getVersion(){
        return version;
    }

    public static Builder newBuilder(){
        return new Builder();
    }



    public static class Builder{

        private Integer id;
        private String name;
        private Integer hours;
        private Integer classRoomId;
        private Integer professorId;
        private boolean active;
        private Integer version;

        private Builder(){}

        public Builder withId(Integer id){
            this.id = id;
            return this;
        }

        public Builder withName(String name){
            this.name = name;
            return this;
        }

        public Builder withHours(Integer hours){
            this.hours = hours;
            return this;
        }

        public Builder withClassRoomId(Integer classRoomId){
            this.classRoomId = classRoomId;
            return this;
        }

        public Builder withProfessorId(Integer professorId){
            this.professorId = professorId;
            return this;
        }

        public Builder withActive(boolean active){
            this.active = active;
            return this;
        }

        public Builder withVersion(Integer version){
            this.version = version;
            return this;
        }

        public Subject build(){

            return new Subject(this);
        }
    }
}
