package com.steve.formulaforecast.service.driver;

import com.neovisionaries.i18n.CountryCode;

import java.time.LocalDate;

public class DriverCreationDetails {
    private final String firstName;
    private final String lastName;
    private final String nickname;
    private final CountryCode nationality;
    private final LocalDate dateOfBirth;

    public DriverCreationDetails(String firstName, String lastName, String nickname, CountryCode nationality, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.nationality = nationality;
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public CountryCode getNationality() {
        return nationality;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
}
