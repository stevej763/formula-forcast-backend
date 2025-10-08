package com.steve.formulaforecast.persistence.entity;

import java.util.UUID;

public record ChampionshipSeasonEntity(
        UUID championshipSeasonUid,
        String championshipName,
        String championshipYear) {

}
