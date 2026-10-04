package com.steve.formulaforecast.service.leaderboard;

import java.util.UUID;

public class LeaderboardEntry {

    private final UUID championshipLeaderboardEntrantUid;
    private final UUID teamUid;
    private final String teamName;
    private final String teamColour;
    private final UUID championshipLeaderboardUid;
    private final long points;

    public LeaderboardEntry(
            UUID championshipLeaderboardEntrantUid,
            UUID teamUid,
            String teamName,
            String teamColour,
            UUID championshipLeaderboardUid,
            long points) {
        this.championshipLeaderboardEntrantUid = championshipLeaderboardEntrantUid;
        this.teamUid = teamUid;
        this.teamName = teamName;
        this.teamColour = teamColour;
        this.championshipLeaderboardUid = championshipLeaderboardUid;
        this.points = points;
    }

    public UUID getChampionshipLeaderboardEntrantUid() {
        return championshipLeaderboardEntrantUid;
    }

    public UUID getTeamUid() {
        return teamUid;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamColour() {
        return teamColour;
    }

    public UUID getChampionshipLeaderboardUid() {
        return championshipLeaderboardUid;
    }

    public long getPoints() {
        return points;
    }
}
