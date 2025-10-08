package com.steve.formulaforecast.api.model.driver;

import com.neovisionaries.i18n.CountryCode;

import java.util.UUID;

public record DriverDetailResponse(
        UUID driverUid,
        String firstName,
        String lastName,
        String nickname,
        CountryCode nationality,
        java.time.LocalDate dateOfBirth) {
}
