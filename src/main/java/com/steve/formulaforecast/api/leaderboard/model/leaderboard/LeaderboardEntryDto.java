package com.steve.formulaforecast.api.leaderboard.model.leaderboard;

import java.util.UUID;

public record LeaderboardEntryDto(
        UUID championshipLeaderboardEntrantUid,
        UUID teamUid,
        String teamName,
        String teamColour,
        UUID championshipLeaderboardUid) {
}
