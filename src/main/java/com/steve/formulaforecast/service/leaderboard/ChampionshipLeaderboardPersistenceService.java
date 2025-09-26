package com.steve.formulaforecast.service.leaderboard;

import com.steve.formulaforecast.persistence.ChampionshipLeaderboardRepository;
import com.steve.formulaforecast.persistence.entity.leaderboard.ChampionshipLeaderboardEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChampionshipLeaderboardPersistenceService {

    private final ChampionshipLeaderboardRepository championshipLeaderboardRepository;

    public ChampionshipLeaderboardPersistenceService(ChampionshipLeaderboardRepository championshipLeaderboardRepository) {
        this.championshipLeaderboardRepository = championshipLeaderboardRepository;
    }

    public List<ChampionshipLeaderboard> getAllLeaderboards() {
        return championshipLeaderboardRepository.selectAllLeaderboards().map(this::toModel).toList();
    }

    public Optional<ChampionshipLeaderboard> selectGlobalLeaderboardForSeason(UUID championshipSeasonUid) {
        return championshipLeaderboardRepository.selectDefaultGlobalLeaderboardForSeason(championshipSeasonUid).map(this::toModel);
    }

    private ChampionshipLeaderboard toModel(ChampionshipLeaderboardEntity championshipLeaderboardEntity) {
        return new ChampionshipLeaderboard(
                championshipLeaderboardEntity.championshipLeaderboardUid(),
                championshipLeaderboardEntity.championshipLeaderboardName(),
                championshipLeaderboardEntity.championshipSeasonUid(),
                championshipLeaderboardEntity.championshipSeasonName(),
                championshipLeaderboardEntity.ownerAccountUid(),
                championshipLeaderboardEntity.leaderboardType(),
                championshipLeaderboardEntity.createdAt()
        );
    }
}
