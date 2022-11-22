package com.project.readingisgood.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class CustomerSaveRequestModel {

    @NotEmpty(message = "Name field cannot be empty")
    private String name;
    @NotEmpty(message = "Email field cannot be empty")
    @Email
    private String email;
    @NotEmpty(message = "Password field cannot be empty")
    private String password;

    public CustomerSaveRequestModel(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
