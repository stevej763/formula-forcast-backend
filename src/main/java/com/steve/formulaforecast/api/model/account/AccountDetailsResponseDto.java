package com.steve.formulaforecast.api.model.account;

import java.util.UUID;

public record AccountDetailsResponseDto(
        UUID accountUid,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        boolean authenticated) {

    public static AccountDetailsResponseDto unauthenticated() {
        return new AccountDetailsResponseDto(null, null, null, null, null, false);
    }
}
