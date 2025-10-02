package com.steve.formulaforecast.api;

import com.steve.formulaforecast.api.model.raceweekend.*;
import com.steve.formulaforecast.service.raceweekends.RaceWeekendDetailsService;
import com.steve.formulaforecast.service.raceweekends.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/race-weekend", produces = APPLICATION_JSON_VALUE)
public class RacesResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(RacesResource.class);

    private final RaceWeekendDetailsService raceWeekendDetailsService;

    RacesResource(RaceWeekendDetailsService raceWeekendDetailsService) {
        this.raceWeekendDetailsService = raceWeekendDetailsService;
    }

    @GetMapping("/all")
    public ResponseEntity<RaceWeekendsResponse> getRaceWeekends() {
        List<RaceWeekendResponse> raceWeekendResponses = raceWeekendDetailsService.getRaceWeekends().stream().map(this::toDto).toList();
        return ResponseEntity.ok(new RaceWeekendsResponse(raceWeekendResponses));
    }

    @GetMapping("/{raceWeekendUid}")
    public ResponseEntity<RaceWeekendResponse> getRaceWeekend(@PathVariable UUID raceWeekendUid) {
        LOGGER.info("searching for race weekend uid=[{}]", raceWeekendUid);
        Optional<RaceWeekendResponse> raceWeekendResponse = raceWeekendDetailsService.getRaceWeekend(raceWeekendUid).map(this::toDto);
        return ResponseEntity.of(raceWeekendResponse);
    }

    @GetMapping("/current")
    public ResponseEntity<CurrentRaceWeekendResponse> getCurrentRaceWeekend() {
        LOGGER.info("finding current race weekend");
        Optional<RaceWeekendResponse> raceWeekendResponse = raceWeekendDetailsService.getRaceCurrentWeekend().map(this::toDto);
        return ResponseEntity.ok(new CurrentRaceWeekendResponse(raceWeekendResponse.orElse(null)));
    }

    @GetMapping("/live")
    public ResponseEntity<LiveRaceWeekendResponse> getLiveRaceWeekend() {
        LOGGER.info("finding live race weekend");
        Optional<RaceWeekendResponse> raceWeekendResponse = raceWeekendDetailsService.getLiveRaceWeekend().map(this::toDto);
        return ResponseEntity.ok(new LiveRaceWeekendResponse(raceWeekendResponse.orElse(null)));
    }

    @GetMapping("/next")
    public ResponseEntity<NextRaceWeekendResponse> getNextRaceWeekend() {
        LOGGER.info("searching for upcoming race weekend");
        Optional<RaceWeekendResponse> raceWeekendResponse = raceWeekendDetailsService.getNextRaceWeekend().map(this::toDto);
        return ResponseEntity.ok(new NextRaceWeekendResponse(raceWeekendResponse.orElse(null)));
    }

    private RaceWeekendResponse toDto(RaceWeekend raceWeekend) {
        return new RaceWeekendResponse(
                raceWeekend.getRaceWeekendUid(),
                raceWeekend.getRaceName().name(),
                raceWeekend.getRaceLocation().getName(),
                raceWeekend.getPracticeSessions().stream().map(this::toPracticeDto).toList(),
                toQualiResponse(raceWeekend.getQualifying()),
                raceWeekend.getSprint().map(this::toSprintResponse).orElse(null),
                toRaceResponse(raceWeekend.getRace()),
                raceWeekend.getRaceWeekendStartDate(),
                raceWeekend.getRaceWeekendEndDate(),
                raceWeekend.getRaceWeekendStatus().getRaceWeekendState().name(),
                raceWeekend.getRaceWeekendStatus().getEventTime()
        );
    }

    private RaceResponse toRaceResponse(Race race) {
        return new RaceResponse(race.raceStartDate(), race.raceSessionUid());
    }

    private SprintResponse toSprintResponse(Sprint sprint) {
        return new SprintResponse(UUID.randomUUID(), LocalDate.of(2025, 1, 1));
    }

    private QualifyingResponse toQualiResponse(Qualifying qualifying) {
        return new QualifyingResponse(qualifying.sessionDate(), qualifying.qualifyingSessionUid());
    }

    private PracticeSessionResponse toPracticeDto(PracticeSession practiceSession) {
        return new PracticeSessionResponse(practiceSession.practiceSessionUid(), practiceSession.practiceSessionNumber(), practiceSession.sessionDate());
    }
}
