package com.steve.formulaforecast.api.season.model.championshipseason;

import java.util.UUID;

public record ChampionshipSeasonDto(
        UUID championshipSeasonUid,
        String championshipName,
        String championshipYear
) {
}
