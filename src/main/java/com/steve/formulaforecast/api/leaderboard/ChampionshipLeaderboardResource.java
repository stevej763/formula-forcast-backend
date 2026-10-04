package com.steve.formulaforecast.api.leaderboard;

import com.steve.formulaforecast.api.leaderboard.model.leaderboard.ChampionshipLeaderboardDto;
import com.steve.formulaforecast.api.leaderboard.model.leaderboard.GlobalChampionshipLeaderboardResponse;
import com.steve.formulaforecast.api.leaderboard.model.leaderboard.ChampionshipLeaderboardsListResponse;
import com.steve.formulaforecast.api.leaderboard.model.leaderboard.LeaderboardEntryDto;
import com.steve.formulaforecast.service.leaderboard.ChampionshipLeaderboard;
import com.steve.formulaforecast.service.leaderboard.ChampionshipLeaderboardWithEntries;
import com.steve.formulaforecast.service.leaderboard.LeaderboardEntry;
import com.steve.formulaforecast.service.leaderboard.LeaderboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
        List<LeaderboardEntryDto> entries = toStandings(currentGlobalLeaderboard.getEntries());
        return ResponseEntity.ok(new GlobalChampionshipLeaderboardResponse(championshipLeaderboardDto, currentGlobalLeaderboard.getEntries().size(), entries));
    }

    /**
     * Positions entries already sorted by points. Tied teams share a position and the next one skips, e.g. 1, 2, 2, 4.
     */
    private List<LeaderboardEntryDto> toStandings(List<LeaderboardEntry> entries) {
        List<LeaderboardEntryDto> standings = new ArrayList<>();
        for (int i = 0; i < entries.size(); i++) {
            LeaderboardEntry entry = entries.get(i);
            boolean tiedWithPrevious = i > 0 && entries.get(i - 1).getPoints() == entry.getPoints();
            int position = tiedWithPrevious ? standings.get(i - 1).position() : i + 1;
            standings.add(toDto(entry, position));
        }
        return standings;
    }

    private LeaderboardEntryDto toDto(LeaderboardEntry leaderboardEntry, int position) {
        return new LeaderboardEntryDto(
                leaderboardEntry.getChampionshipLeaderboardEntrantUid(),
                leaderboardEntry.getTeamUid(),
                leaderboardEntry.getTeamName(),
                leaderboardEntry.getTeamColour(),
                leaderboardEntry.getChampionshipLeaderboardUid(),
                position,
                leaderboardEntry.getPoints());
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
