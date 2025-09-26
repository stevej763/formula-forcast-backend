package com.steve.formulaforecast.service.leaderboard;

import java.util.List;

public class ChampionshipLeaderboardWithEntries {

    private final ChampionshipLeaderboard championshipLeaderboard;
    private final List<LeaderboardEntry> entries;

    public ChampionshipLeaderboardWithEntries(ChampionshipLeaderboard championshipLeaderboard, List<LeaderboardEntry> entries) {
        this.championshipLeaderboard = championshipLeaderboard;
        this.entries = entries;
    }

    public ChampionshipLeaderboard getChampionshipLeaderboard() {
        return championshipLeaderboard;
    }

    public List<LeaderboardEntry> getEntries() {
        return entries;
    }
}
