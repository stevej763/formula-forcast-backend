package com.steve.formulaforecast.api.model.team;

import java.util.UUID;

public record TeamDetailsDto(UUID teamUid, String teamName, String teamColour) {
}
