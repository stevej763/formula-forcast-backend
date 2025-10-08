package com.steve.formulaforecast.api.userteam.userteam;

import java.util.UUID;

public record TeamDetailsDto(UUID teamUid, String teamName, String teamColour) {
}
