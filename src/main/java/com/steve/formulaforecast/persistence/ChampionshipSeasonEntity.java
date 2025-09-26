package com.steve.formulaforecast.persistence;

import java.util.UUID;

public record ChampionshipSeasonEntity(
        UUID championshipSeasonUid,
        String championshipSeasonName,
        String championshipSeasonYear) {

}
