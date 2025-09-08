package com.steve.formulaforecast.service.driver;

import com.neovisionaries.i18n.CountryCode;

import java.util.UUID;

public class DriverDetails {
    private final UUID driverUid;
    private final String firstName;
    private final String lastName;
    private final CountryCode nationality;
    private final String nickname;

    public DriverDetails(UUID driverUid, String firstName, String lastName, CountryCode nationality, String nickname) {
        this.driverUid = driverUid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.nickname = nickname;
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
}
