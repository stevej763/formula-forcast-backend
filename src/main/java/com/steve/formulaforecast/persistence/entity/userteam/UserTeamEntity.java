package com.steve.formulaforecast.persistence.entity.userteam;

import java.util.UUID;

public record UserTeamEntity(UUID teamUid, String teamName, String teamColour, UUID accountUid) {
}
