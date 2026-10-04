package com.steve.formulaforecast.api.season;

import com.steve.formulaforecast.api.exception.RequestValidationException;
import com.steve.formulaforecast.api.season.model.championshipseason.ChampionshipSeasonCreationRequest;
import com.steve.formulaforecast.api.season.model.championshipseason.ChampionshipSeasonDto;
import com.steve.formulaforecast.api.season.model.championshipseason.ChampionshipSeasonsResponse;
import com.steve.formulaforecast.service.leaderboard.ChampionshipSeason;
import com.steve.formulaforecast.service.leaderboard.ChampionshipSeasonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.isNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/championship-season", produces = APPLICATION_JSON_VALUE)
public class ChampionshipSeasonResource {

    private static final String INVALID_SEASON_YEAR = "INVALID_SEASON_YEAR";
    private static final String INVALID_SEASON_NAME = "INVALID_SEASON_NAME";

    private final ChampionshipSeasonService championshipSeasonService;

    ChampionshipSeasonResource(ChampionshipSeasonService championshipSeasonService) {
        this.championshipSeasonService = championshipSeasonService;
    }

    @GetMapping("/all")
    public ResponseEntity<ChampionshipSeasonsResponse> getAllSeasons() {
        List<ChampionshipSeasonDto> allSeasons = championshipSeasonService.getAllSeasons().stream().map(this::toDto).toList();
        ChampionshipSeasonsResponse championshipSeasonsResponse = new ChampionshipSeasonsResponse(allSeasons);
        return ResponseEntity.ok(championshipSeasonsResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<ChampionshipSeasonDto> createSeason(@RequestBody ChampionshipSeasonCreationRequest championshipSeasonCreationRequest) {
        validate(championshipSeasonCreationRequest);
        ChampionshipSeason season = championshipSeasonService.createSeason(
                championshipSeasonCreationRequest.year(),
                championshipSeasonCreationRequest.name().trim());
        return ResponseEntity.ok(toDto(season));
    }

    private void validate(ChampionshipSeasonCreationRequest championshipSeasonCreationRequest) {
        if (championshipSeasonCreationRequest.year() < 1950 || championshipSeasonCreationRequest.year() > 2100) {
            throw new RequestValidationException(INVALID_SEASON_YEAR);
        }
        if (isNull(championshipSeasonCreationRequest.name()) || championshipSeasonCreationRequest.name().isBlank()) {
            throw new RequestValidationException(INVALID_SEASON_NAME);
        }
    }

    private ChampionshipSeasonDto toDto(ChampionshipSeason championshipSeason) {
        return new ChampionshipSeasonDto(
                championshipSeason.getChampionshipSeasonUid(),
                championshipSeason.getChampionshipName(),
                championshipSeason.getChampionshipYear());
    }
}
