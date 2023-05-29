package com.ilegra.university.model;

import java.io.Serializable;

public class Student implements Serializable {

    private final static Long serialVersionUID = 1L;

    private final Integer id;
    private final String name;
    private final String email;
    private final String phone;
    private final String cpf;



    private Student(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.email = builder.email;
        this.phone = builder.phone;
        this.cpf = builder.cpf;
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



    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{
        private Integer id;
        private String name;
        private String email;
        private String phone;
        private String cpf;

        private Builder() {}

        public Builder withId(Integer id){
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
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

        public Student build(){

            return new Student(this);
        }
    }
}
