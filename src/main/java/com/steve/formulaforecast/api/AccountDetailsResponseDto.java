package com.steve.formulaforecast.api;

import java.util.UUID;

public record AccountDetailsResponseDto(
        UUID accountUid,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        boolean authenticated) {
}
