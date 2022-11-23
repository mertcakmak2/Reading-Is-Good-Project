package com.project.readingisgood.model.request;

import javax.validation.constraints.NotNull;

public class LoginRequestModel {

    @NotNull(message = "Email cannot be empty.")
    private String email;
    @NotNull(message = "Password cannot be empty.")
    private String password;

    public LoginRequestModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginRequestModel() {
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
