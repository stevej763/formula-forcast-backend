package com.steve.formulaforecast.api.driver.model.driver;

import com.neovisionaries.i18n.CountryCode;

import java.time.LocalDate;
import java.util.UUID;

public record DriverDetailResponse(
        UUID driverUid,
        String firstName,
        String lastName,
        String nickname,
        CountryCode nationality,
        LocalDate dateOfBirth,
        UUID constructorUid,
        String teamName) {
}
