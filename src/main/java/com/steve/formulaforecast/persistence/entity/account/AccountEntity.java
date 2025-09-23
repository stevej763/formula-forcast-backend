package com.steve.formulaforecast.persistence.entity.account;

import java.time.Instant;
import java.util.UUID;

public record AccountEntity(
        UUID accountUid,
        String email,
        String firstName,
        String lastName,
        String phoneNumber,
        Instant createdAt,
        String password
) {
}
