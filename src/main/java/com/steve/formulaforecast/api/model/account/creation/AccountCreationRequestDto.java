package com.steve.formulaforecast.api.model.account.creation;

import java.time.Instant;

public record AccountCreationRequestDto(
        String email,
        String firstName,
        String lastName,
        String phoneNumber,
        String password) {
}
