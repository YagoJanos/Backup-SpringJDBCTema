package com.ilegra.university.model;

import java.io.Serializable;

public class Program implements Serializable {

    private static final Long serialVersionUID = 1L;

    private final Integer id;
    private final String name;
    private final Integer degreeId;
    private final Short totalSemesters;
    private final boolean active;
    private final Integer version;


    private Program(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.degreeId = builder.degreeId;
        this.totalSemesters = builder.totalSemesters;
        this.active = builder.active;
        this.version = builder.version;
    }



    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getDegreeId() {
        return degreeId;
    }

    public Short getTotalSemesters() {
        return totalSemesters;
    }

    public boolean isActive() {
        return active;
    }
    public Integer getVersion(){return version;}

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{
        private Integer id;
        private String name;
        private Integer degreeId;
        private Short totalSemesters;
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

        public Builder withDegreeId(Integer degreeId){
            this.degreeId = degreeId;
            return this;
        }

        public Builder withTotalSemesters(Short totalSemesters){
            this.totalSemesters = totalSemesters;
            return this;
        }

        public Builder withActive(Boolean active){
            this.active = active;
            return this;
        }

        public Builder withVersion(Integer version){
            this.version = version;
            return this;
        }

        public Program build(){
            return new Program(this);
        }
    }
}
