package com.steve.formulaforecast.persistence.entity.leaderboardentity;

import java.util.UUID;

public record LeaderboardEntryEntity(
        UUID championshipLeaderboardEntrantUid,
        UUID championshipLeaderboardUid,
        UUID teamUid,
        String teamName,
        String teamColour
) {
}
