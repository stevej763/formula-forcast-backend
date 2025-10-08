package com.steve.formulaforecast.api.season;

import com.steve.formulaforecast.api.season.model.championshipseason.ChampionshipSeasonDto;
import com.steve.formulaforecast.api.season.model.championshipseason.ChampionshipSeasonsResponse;
import com.steve.formulaforecast.api.constructor.model.constructor.ConstructorCreationRequest;
import com.steve.formulaforecast.service.leaderboard.ChampionshipSeason;
import com.steve.formulaforecast.service.leaderboard.ChampionshipSeasonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/championship-season", produces = APPLICATION_JSON_VALUE)
public class ChampionshipSeasonResource {


    private final ChampionshipSeasonService championshipSeasonService;

    ChampionshipSeasonResource(ChampionshipSeasonService championshipSeasonService) {
        this.championshipSeasonService = championshipSeasonService;
    }

    @GetMapping("/all")
    public ResponseEntity<ChampionshipSeasonsResponse> getAllConstructors() {
        List<ChampionshipSeasonDto> allSeasons = championshipSeasonService.getAllSeasons().stream().map(this::toDto).toList();
        ChampionshipSeasonsResponse championshipSeasonsResponse = new ChampionshipSeasonsResponse(allSeasons);
        return ResponseEntity.ok(championshipSeasonsResponse);
    }

    private ChampionshipSeasonDto toDto(ChampionshipSeason championshipSeason) {
        return new ChampionshipSeasonDto(
                championshipSeason.getChampionshipSeasonUid(),
                championshipSeason.getChampionshipName(),
                championshipSeason.getChampionshipYear());
    }

    @PostMapping("/create")
    public void createConstructor(@RequestBody ConstructorCreationRequest constructorCreationRequest) {

    }
}
