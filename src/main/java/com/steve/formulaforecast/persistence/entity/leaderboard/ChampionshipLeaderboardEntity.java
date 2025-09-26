package com.steve.formulaforecast.persistence.entity.leaderboard;

import com.steve.formulaforecast.service.leaderboard.LeaderboardType;

import java.time.Instant;
import java.util.UUID;

public record ChampionshipLeaderboardEntity(
        UUID championshipLeaderboardUid,
        String championshipLeaderboardName,
        UUID championshipSeasonUid,
        String championshipSeasonName,
        UUID ownerAccountUid,
        String ownerFirstName,
        String ownerLastName,
        Instant createdAt,
        LeaderboardType leaderboardType
) {
}
