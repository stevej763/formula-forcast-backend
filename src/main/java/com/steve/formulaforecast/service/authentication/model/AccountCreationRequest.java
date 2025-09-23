package com.steve.formulaforecast.service.authentication.model;

import java.util.UUID;

public class AccountCreationRequest {

    private final UUID accountUid;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;
    private final String password;

    public AccountCreationRequest(
            UUID accountUid,
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            String password) {
        this.accountUid = accountUid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public UUID getAccountUid() {
        return accountUid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }
}
