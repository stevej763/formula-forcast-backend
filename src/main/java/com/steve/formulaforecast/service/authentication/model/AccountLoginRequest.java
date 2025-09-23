package com.steve.formulaforecast.service.authentication.model;

public class AccountLoginRequest {

    private final String email;
    private final String password;

    public AccountLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
