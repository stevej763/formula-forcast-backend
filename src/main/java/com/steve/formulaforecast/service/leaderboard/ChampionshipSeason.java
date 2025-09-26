package com.steve.formulaforecast.service.leaderboard;

import java.util.UUID;

public class ChampionshipSeason {

    private final UUID championshipSeasonUid;
    private final String championshipSeasonName;
    private final String championshipSeasonYear;

    public ChampionshipSeason(
            UUID championshipSeasonUid,
            String championshipSeasonName,
            String championshipSeasonYear) {
        this.championshipSeasonUid = championshipSeasonUid;
        this.championshipSeasonName = championshipSeasonName;
        this.championshipSeasonYear = championshipSeasonYear;
    }

    public UUID getChampionshipSeasonUid() {
        return championshipSeasonUid;
    }

    public String getChampionshipSeasonName() {
        return championshipSeasonName;
    }

    public String getChampionshipSeasonYear() {
        return championshipSeasonYear;
    }
}
