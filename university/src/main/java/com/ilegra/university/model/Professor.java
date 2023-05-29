package com.ilegra.university.model;

import java.io.Serializable;

public class Professor implements Serializable {

    private static final Long serialVersionUID = 1L;

    private final Integer id;
    private final String name;
    private final String email;
    private final String phone;
    private final String cpf;
    private final Integer subjectId;



    private Professor(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.email = builder.email;
        this.phone = builder.phone;
        this.cpf = builder.cpf;
        this.subjectId = builder.subjectId;
    }



    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCpf() {
        return cpf;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{
        private Integer id;
        private String name;
        private String email;
        private String phone;
        private String cpf;
        private Integer subjectId;

        private Builder(){}

        public Builder withId(Integer Id){
            this.id = id;
            return this;
        }

        public Builder withName(String name){
            this.name = name;
            return this;
        }

        public Builder withEmail(String email){
            this.email = email;
            return this;
        }

        public Builder withPhone(String phone){
            this.phone = phone;
            return this;
        }

        public Builder withCpf(String cpf){
            this.cpf = cpf;
            return this;
        }

        public Builder withSubjectId(Integer subjectId){
            this.subjectId = subjectId;
            return this;
        }

        public Professor build(){

            return new Professor(this);
        }
    }
}
