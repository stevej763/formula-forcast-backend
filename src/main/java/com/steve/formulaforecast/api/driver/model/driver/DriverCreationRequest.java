package com.steve.formulaforecast.api.driver.model.driver;

import com.neovisionaries.i18n.CountryCode;
import jakarta.annotation.Nonnull;

import java.time.LocalDate;

public record DriverCreationRequest(
        @Nonnull String firstName,
        @Nonnull String lastName,
        @Nonnull String nickname,
        @Nonnull CountryCode nationality,
        @Nonnull LocalDate dateOfBirth
) {
}
