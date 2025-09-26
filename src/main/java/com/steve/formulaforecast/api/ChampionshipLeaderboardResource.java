package com.steve.formulaforecast.api;

import com.steve.formulaforecast.api.model.leaderboard.ChampionshipLeaderboardDto;
import com.steve.formulaforecast.api.model.leaderboard.GlobalChampionshipLeaderboardResponse;
import com.steve.formulaforecast.api.model.leaderboard.ChampionshipLeaderboardsListResponse;
import com.steve.formulaforecast.service.leaderboard.ChampionshipLeaderboard;
import com.steve.formulaforecast.service.leaderboard.ChampionshipLeaderboardWithEntries;
import com.steve.formulaforecast.service.leaderboard.LeaderboardEntry;
import com.steve.formulaforecast.service.leaderboard.LeaderboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/championship-leaderboard", produces = APPLICATION_JSON_VALUE)
public class ChampionshipLeaderboardResource {

    private final LeaderboardService leaderboardService;

    public ChampionshipLeaderboardResource(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @GetMapping("/all")
    public ResponseEntity<ChampionshipLeaderboardsListResponse> getAllLeaderboards() {
        List<ChampionshipLeaderboardDto> leaderboardList = leaderboardService.getAllLeaderboards().stream().map(this::toDto).toList();
        return ResponseEntity.ok(new ChampionshipLeaderboardsListResponse(leaderboardList));
    }

    @GetMapping("/global")
    public ResponseEntity<GlobalChampionshipLeaderboardResponse> getCurrentGlobalLeaderboard() {
        ChampionshipLeaderboardWithEntries currentGlobalLeaderboard = leaderboardService.getCurrentGlobalLeaderboard();
        ChampionshipLeaderboardDto championshipLeaderboardDto = toDto(currentGlobalLeaderboard.getChampionshipLeaderboard());
        List<LeaderboardEntryDto> entries = currentGlobalLeaderboard.getEntries().stream().map(leaderboardEntry -> toDto(leaderboardEntry)).toList();
        return ResponseEntity.ok(new GlobalChampionshipLeaderboardResponse(championshipLeaderboardDto, currentGlobalLeaderboard.getEntries().size(), entries));
    }

    private LeaderboardEntryDto toDto(LeaderboardEntry leaderboardEntry) {
        return new LeaderboardEntryDto(
                leaderboardEntry.getChampionshipLeaderboardEntrantUid(),
                leaderboardEntry.getTeamUid(),
                leaderboardEntry.getTeamName(),
                leaderboardEntry.getTeamColour(),
                leaderboardEntry.getChampionshipLeaderboardUid());
    }

    private ChampionshipLeaderboardDto toDto(ChampionshipLeaderboard championshipLeaderboard) {
        return new ChampionshipLeaderboardDto(
                championshipLeaderboard.getLeaderboardUid(),
                championshipLeaderboard.getLeaderboardName(),
                championshipLeaderboard.getChampionshipSeasonUid(),
                championshipLeaderboard.getChampionshipSeasonName(),
                championshipLeaderboard.getLeaderboardType(),
                championshipLeaderboard.getCreatedAt());
    }

}
