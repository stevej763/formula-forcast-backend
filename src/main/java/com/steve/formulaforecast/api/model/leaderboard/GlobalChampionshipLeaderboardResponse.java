package com.steve.formulaforecast.api.model.leaderboard;

import com.steve.formulaforecast.api.LeaderboardEntryDto;
import com.steve.formulaforecast.api.model.team.TeamDetailsDto;

import java.util.List;

public record GlobalChampionshipLeaderboardResponse(
        ChampionshipLeaderboardDto championshipLeaderboard,
        int entries,
        List<LeaderboardEntryDto> topTen
) {
}
