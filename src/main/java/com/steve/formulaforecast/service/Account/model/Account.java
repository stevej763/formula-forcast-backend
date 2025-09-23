package com.steve.formulaforecast.service.Account.model;

import java.util.UUID;

public class Account {
    private final UUID accountUid;
    private final String firstName;
    private final String lastName;
    private final String email;

    public Account(UUID accountUid, String firstName, String lastName, String email) {
        this.accountUid = accountUid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
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
}
