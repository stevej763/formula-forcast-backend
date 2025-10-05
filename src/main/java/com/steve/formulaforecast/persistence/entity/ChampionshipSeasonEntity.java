package com.steve.formulaforecast.persistence.entity;

import java.util.UUID;

public record ChampionshipSeasonEntity(
        UUID championshipSeasonUid,
        String championshipSeasonName,
        String championshipSeasonYear) {

}
