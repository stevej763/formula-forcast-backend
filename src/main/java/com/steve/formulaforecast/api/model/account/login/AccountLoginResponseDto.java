package com.steve.formulaforecast.api.model.account.login;

public record AccountLoginResponseDto(java.util.UUID accountUid, String accessToken, long expirationTime) {
}
