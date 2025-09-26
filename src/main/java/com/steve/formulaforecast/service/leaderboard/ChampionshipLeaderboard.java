package com.steve.formulaforecast.service.leaderboard;

import java.time.Instant;
import java.util.UUID;

public class ChampionshipLeaderboard {

    private final UUID leaderboardUid;
    private final String leaderboardName;
    private final UUID championshipSeasonUid;
    private final String championshipSeasonName;
    private final UUID createdBy;
    private final LeaderboardType leaderboardType;
    private final Instant createdAt;

    public ChampionshipLeaderboard(
            UUID leaderboardUid,
            String leaderboardName,
            UUID championshipSeasonUid,
            String championshipSeasonName,
            UUID createdBy,
            LeaderboardType leaderboardType,
            Instant createdAt) {
        this.leaderboardUid = leaderboardUid;
        this.leaderboardName = leaderboardName;
        this.championshipSeasonUid = championshipSeasonUid;
        this.championshipSeasonName = championshipSeasonName;
        this.createdBy = createdBy;
        this.leaderboardType = leaderboardType;
        this.createdAt = createdAt;
    }

    public UUID getLeaderboardUid() {
        return leaderboardUid;
    }

    public String getLeaderboardName() {
        return leaderboardName;
    }

    public UUID getChampionshipSeasonUid() {
        return championshipSeasonUid;
    }

    public String getChampionshipSeasonName() {
        return championshipSeasonName;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public LeaderboardType getLeaderboardType() {
        return leaderboardType;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
