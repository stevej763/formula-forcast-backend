package com.steve.formulaforecast.api.model.leaderboard;

import com.steve.formulaforecast.service.leaderboard.LeaderboardType;

import java.time.Instant;
import java.util.UUID;

public record ChampionshipLeaderboardDto(
        UUID leaderboardUid,
        String leaderboardName,
        UUID championshipSeasonUid,
        String championshipSeasonName,
        LeaderboardType leaderboardType,
        Instant createdAt) {
}
