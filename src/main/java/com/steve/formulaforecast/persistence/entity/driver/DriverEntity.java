package com.steve.formulaforecast.persistence.entity.driver;

import com.neovisionaries.i18n.CountryCode;

import java.util.UUID;

public record DriverEntity(UUID driverUid, String firstName, String lastName, String nickname, CountryCode nationality) {
}
