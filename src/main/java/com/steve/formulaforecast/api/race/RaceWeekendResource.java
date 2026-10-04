package com.steve.formulaforecast.api.race;

import com.steve.formulaforecast.api.race.model.raceweekend.CurrentRaceWeekendResponse;
import com.steve.formulaforecast.api.race.model.raceweekend.LiveRaceWeekendResponse;
import com.steve.formulaforecast.api.race.model.raceweekend.NextRaceWeekendResponse;
import com.steve.formulaforecast.api.race.model.raceweekend.PracticeSessionResponse;
import com.steve.formulaforecast.api.race.model.raceweekend.QualifyingResponse;
import com.steve.formulaforecast.api.race.model.raceweekend.RaceResponse;
import com.steve.formulaforecast.api.race.model.raceweekend.RaceWeekendCreationRequest;
import com.steve.formulaforecast.api.race.model.raceweekend.RaceWeekendResponse;
import com.steve.formulaforecast.api.race.model.raceweekend.RaceWeekendsResponse;
import com.steve.formulaforecast.api.race.model.raceweekend.SprintResponse;
import com.neovisionaries.i18n.CountryCode;
import com.steve.formulaforecast.api.exception.RequestValidationException;
import com.steve.formulaforecast.service.raceweekends.RaceWeekendDetailsService;
import com.steve.formulaforecast.service.raceweekends.model.PracticeSession;
import com.steve.formulaforecast.service.raceweekends.model.Qualifying;
import com.steve.formulaforecast.service.raceweekends.model.Race;
import com.steve.formulaforecast.service.raceweekends.model.RaceName;
import com.steve.formulaforecast.service.raceweekends.model.RaceWeekend;
import com.steve.formulaforecast.service.raceweekends.model.Sprint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/race-weekend", produces = APPLICATION_JSON_VALUE)
public class RaceWeekendResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(RaceWeekendResource.class);
    private static final String INVALID_RACE_NAME = "INVALID_RACE_NAME";
    private static final String INVALID_RACE_LOCATION = "INVALID_RACE_LOCATION";
    private static final String INVALID_RACE_WEEKEND_DATES = "INVALID_RACE_WEEKEND_DATES";
    private static final String INVALID_SESSION_TIMES = "INVALID_SESSION_TIMES";

    private final RaceWeekendDetailsService raceWeekendDetailsService;

    RaceWeekendResource(RaceWeekendDetailsService raceWeekendDetailsService) {
        this.raceWeekendDetailsService = raceWeekendDetailsService;
    }

    @GetMapping("/all")
    public ResponseEntity<RaceWeekendsResponse> getRaceWeekends() {
        List<RaceWeekendResponse> raceWeekendResponses = raceWeekendDetailsService.getRaceWeekends().stream().map(this::toDto).toList();
        return ResponseEntity.ok(new RaceWeekendsResponse(raceWeekendResponses));
    }

    @GetMapping("/current-season")
    public ResponseEntity<RaceWeekendsResponse> getRaceWeekendsForCurrentSeason() {
        List<RaceWeekendResponse> raceWeekendResponses = raceWeekendDetailsService.getRaceWeekendsForCurrentSeason().stream().map(this::toDto).toList();
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

    @PostMapping
    public ResponseEntity<RaceWeekendResponse> createRaceWeekend(@RequestBody RaceWeekendCreationRequest raceWeekendCreationRequest) {
        LOGGER.info("Creating race weekend with values=[{}]", raceWeekendCreationRequest);
        RaceName raceName = toRaceName(raceWeekendCreationRequest.raceName());
        CountryCode raceLocation = toCountryCode(raceWeekendCreationRequest.raceLocation());
        LocalDate startDate = raceWeekendCreationRequest.raceWeekendStartDate();
        LocalDate endDate = raceWeekendCreationRequest.raceWeekendEndDate();
        if (startDate == null || endDate == null || endDate.isBefore(startDate)) {
            throw new RequestValidationException(INVALID_RACE_WEEKEND_DATES);
        }
        Instant qualifyingStartsAt = raceWeekendCreationRequest.qualifyingStartsAt();
        Instant raceStartsAt = raceWeekendCreationRequest.raceStartsAt();
        if (qualifyingStartsAt == null || raceStartsAt == null || !qualifyingStartsAt.isBefore(raceStartsAt)) {
            throw new RequestValidationException(INVALID_SESSION_TIMES);
        }
        RaceWeekend raceWeekend = raceWeekendDetailsService.createRaceWeekend(raceName, raceLocation, startDate, endDate, qualifyingStartsAt, raceStartsAt);
        return ResponseEntity.ok(toDto(raceWeekend));
    }

    private RaceName toRaceName(String raceName) {
        if (raceName == null) {
            throw new RequestValidationException(INVALID_RACE_NAME);
        }
        try {
            return RaceName.valueOf(raceName.trim().toUpperCase().replace(' ', '_'));
        } catch (IllegalArgumentException e) {
            throw new RequestValidationException(INVALID_RACE_NAME);
        }
    }

    private CountryCode toCountryCode(String raceLocation) {
        CountryCode countryCode = raceLocation == null ? null : CountryCode.getByCode(raceLocation.trim(), false);
        if (countryCode == null || countryCode == CountryCode.UNDEFINED) {
            throw new RequestValidationException(INVALID_RACE_LOCATION);
        }
        return countryCode;
    }

    private RaceWeekendResponse toDto(RaceWeekend raceWeekend) {
        return new RaceWeekendResponse(
                raceWeekend.getRaceWeekendUid(),
                raceWeekend.getRoundNumber(),
                raceWeekend.getRaceName().name(),
                raceWeekend.getRaceLocation().getName(),
                raceWeekend.getPracticeSessions().stream().map(this::toPracticeDto).toList(),
                toQualiResponse(raceWeekend.getQualifying()),
                raceWeekend.getSprint().map(this::toSprintResponse).orElse(null),
                toRaceResponse(raceWeekend.getRace()),
                raceWeekend.getRaceWeekendStartDate(),
                raceWeekend.getRaceWeekendEndDate(),
                raceWeekend.getRaceWeekendStatus().getRaceWeekendState().name(),
                raceWeekend.getRaceWeekendStatus().getEventTime(),
                raceWeekend.getPredictionsLockAt()
        );
    }

    private RaceResponse toRaceResponse(Race race) {
        return new RaceResponse(race.startsAt(), race.raceSessionUid());
    }

    private SprintResponse toSprintResponse(Sprint sprint) {
        return new SprintResponse(sprint.sprintSessionUid(), sprint.startsAt());
    }

    private QualifyingResponse toQualiResponse(Qualifying qualifying) {
        return new QualifyingResponse(qualifying.startsAt(), qualifying.qualifyingSessionUid());
    }

    private PracticeSessionResponse toPracticeDto(PracticeSession practiceSession) {
        return new PracticeSessionResponse(practiceSession.practiceSessionUid(), practiceSession.practiceSessionNumber(), practiceSession.startsAt());
    }
}
