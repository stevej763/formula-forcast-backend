package com.steve.formulaforecast.service.leaderboard;

import java.util.UUID;

public class ChampionshipSeason {

    private final UUID championshipSeasonUid;
    private final String championshipName;
    private final String championshipYear;

    public ChampionshipSeason(
            UUID championshipSeasonUid,
            String championshipName,
            String championshipYear) {
        this.championshipSeasonUid = championshipSeasonUid;
        this.championshipName = championshipName;
        this.championshipYear = championshipYear;
    }

    public UUID getChampionshipSeasonUid() {
        return championshipSeasonUid;
    }

    public String getChampionshipName() {
        return championshipName;
    }

    public String getChampionshipYear() {
        return championshipYear;
    }
}
