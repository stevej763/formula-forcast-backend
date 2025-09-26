package com.steve.formulaforecast.service.leaderboard;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LeaderboardService {

    private final ChampionshipLeaderboardPersistenceService championshipLeaderboardPersistenceService;
    private final LeaderboardEntryPersistenceService leaderboardEntryPersistenceService;
    private final ChampionshipSeasonService championshipSeasonService;


    public LeaderboardService(
            ChampionshipLeaderboardPersistenceService championshipLeaderboardPersistenceService,
            LeaderboardEntryPersistenceService leaderboardEntryPersistenceService,
            ChampionshipSeasonService championshipSeasonService) {
        this.championshipLeaderboardPersistenceService = championshipLeaderboardPersistenceService;
        this.leaderboardEntryPersistenceService = leaderboardEntryPersistenceService;
        this.championshipSeasonService = championshipSeasonService;
    }

    @Transactional
    public List<ChampionshipLeaderboard> getAllLeaderboards() {
        return championshipLeaderboardPersistenceService.getAllLeaderboards();
    }

    @Transactional
    public ChampionshipLeaderboardWithEntries getCurrentGlobalLeaderboard() {
        ChampionshipLeaderboard championshipLeaderboard = getCurrentGlobalChampionshipLeaderboard();
        UUID leaderboardUid = championshipLeaderboard.getLeaderboardUid();
        List<LeaderboardEntry> entries = leaderboardEntryPersistenceService.getTeamsInLeaderboard(leaderboardUid);
        return new ChampionshipLeaderboardWithEntries(championshipLeaderboard, entries);
    }

    @Transactional
    public void addTeamToGlobalLeaderboard(UUID userTeamUid) {
        ChampionshipLeaderboard currentGlobalLeaderboard = getCurrentGlobalChampionshipLeaderboard();
        leaderboardEntryPersistenceService.addTeamToLeaderboard(currentGlobalLeaderboard.getLeaderboardUid(), userTeamUid, UUID.randomUUID());
    }

    private ChampionshipLeaderboard getCurrentGlobalChampionshipLeaderboard() {
        ChampionshipSeason currentSeason = championshipSeasonService.getCurrentSeason();
        return championshipLeaderboardPersistenceService.selectGlobalLeaderboardForSeason(currentSeason.getChampionshipSeasonUid())
                .orElseThrow();
    }
}
