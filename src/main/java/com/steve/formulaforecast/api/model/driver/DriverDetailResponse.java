package com.steve.formulaforecast.api.model.driver;

public record DriverDetailResponse(java.util.UUID driverUid, String firstName, String lastName, String nickname,
                                   com.neovisionaries.i18n.CountryCode nationality) {
}
