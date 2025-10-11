package com.steve.formulaforecast.service.driver;

import com.neovisionaries.i18n.CountryCode;

import java.time.LocalDate;
import java.util.UUID;

public class DriverDetails {

    private final UUID driverUid;
    private final String firstName;
    private final String lastName;
    private final CountryCode nationality;
    private final String nickname;
    private final LocalDate dateOfBirth;
    private final UUID constructorUid;
    private final String teamName;

    public DriverDetails(
            UUID driverUid,
            String firstName,
            String lastName,
            CountryCode nationality,
            String nickname,
            LocalDate dateOfBirth,
            UUID constructorUid,
            String teamName) {
        this.driverUid = driverUid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.nickname = nickname;
        this.dateOfBirth = dateOfBirth;
        this.constructorUid = constructorUid;
        this.teamName = teamName;
    }

    public UUID getDriverUid() {
        return driverUid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public CountryCode getNationality() {
        return nationality;
    }

    public String getNickname() {
        return nickname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public UUID getConstructorUid() {
        return constructorUid;
    }

    public String getTeamName() {
        return teamName;
    }
}
