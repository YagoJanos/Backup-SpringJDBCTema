package com.ilegra.university.model;

import java.io.Serializable;

public class Degree implements Serializable {
    private static final Long serialVersionUID = 1L;

    private final Integer id;
    private final String name;



    private Degree(Builder builder){
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

        public Degree build(){

            return new Degree(this);
        }
    }
}
