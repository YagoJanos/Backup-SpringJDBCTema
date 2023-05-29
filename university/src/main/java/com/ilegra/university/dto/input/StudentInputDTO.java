package com.ilegra.university.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class StudentInputDTO {

    @NotBlank
    @Size(max=255)
    private String name;

    @NotBlank
    @Email
    @Size(max=255)
    private String email;

    @Size(max=30)
    private String phone;

    @NotBlank
    @Size(min=11, max=11)
    private String cpf;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
