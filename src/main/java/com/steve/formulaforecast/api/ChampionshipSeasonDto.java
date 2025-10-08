package com.steve.formulaforecast.api;

import java.util.UUID;

public record ChampionshipSeasonDto(
        UUID championshipSeasonUid,
        String championshipName,
        String championshipYear
) {
}
