package com.steve.formulaforecast.api.leaderboard.model.leaderboard;

import java.util.List;

public record GlobalChampionshipLeaderboardResponse(
        ChampionshipLeaderboardDto championshipLeaderboard,
        int entries,
        List<LeaderboardEntryDto> topTen
) {
}
