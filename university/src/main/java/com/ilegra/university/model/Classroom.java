package com.ilegra.university.model;

import java.io.Serializable;

public class Classroom implements Serializable {

    private static final Long serialVersionUID = 1L;

    private final Integer id;
    private final String name;



    private Classroom(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
    }



    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }



    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private Integer id;
        private String name;

        private Builder(){}

        public Builder withId(Integer id){
            this.id = id;
            return this;
        }

        public Builder withName(String name){
            this.name = name;
            return this;
        }

        public Classroom build(){

            return new Classroom(this);
        }
    }
}
