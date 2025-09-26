package com.steve.formulaforecast.service.leaderboard;

import com.steve.formulaforecast.persistence.LeaderboardEntryRepository;
import com.steve.formulaforecast.persistence.entity.leaderboardEntity.LeaderboardEntryEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.InstantSource;
import java.util.List;
import java.util.UUID;

@Service
public class LeaderboardEntryPersistenceService {


    private static final Logger LOGGER = LoggerFactory.getLogger(LeaderboardEntryPersistenceService.class);
    private final LeaderboardEntryRepository leaderboardEntryRepository;
    private final InstantSource instantSource;

    public LeaderboardEntryPersistenceService(
            LeaderboardEntryRepository leaderboardEntryRepository,
            InstantSource instantSource) {
        this.leaderboardEntryRepository = leaderboardEntryRepository;
        this.instantSource = instantSource;
    }

    public void addTeamToLeaderboard(UUID leaderboardUid, UUID userTeamUid, UUID leaderboardEntryUid) {
        int inserted = leaderboardEntryRepository.addTeamToLeaderboard(leaderboardUid, userTeamUid, leaderboardEntryUid, instantSource.instant());
        if (inserted == 1) {
            LOGGER.info("Added team {} to leaderboard {}", userTeamUid, leaderboardUid);
        }
    }

    public List<LeaderboardEntry> getTeamsInLeaderboard(UUID leaderboardUid) {
        return leaderboardEntryRepository.getTeamsInLeaderboard(leaderboardUid).map(this::toModel).toList();
    }

    private LeaderboardEntry toModel(LeaderboardEntryEntity leaderboardEntryEntity) {
        return new LeaderboardEntry(
                leaderboardEntryEntity.championshipLeaderboardEntrantUid(),
                leaderboardEntryEntity.teamUid(),
                leaderboardEntryEntity.teamName(),
                leaderboardEntryEntity.teamColour(),
                leaderboardEntryEntity.championshipLeaderboardUid());
    }
}
