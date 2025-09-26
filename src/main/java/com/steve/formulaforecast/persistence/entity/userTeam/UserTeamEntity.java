package com.steve.formulaforecast.persistence.entity.userTeam;

import java.util.UUID;

public record UserTeamEntity(UUID teamUid, String teamName, String teamColour, UUID accountUid) {
}
