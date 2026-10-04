package com.steve.formulaforecast.service.prediction;

import com.steve.formulaforecast.api.prediction.model.prediction.RankedDriverPrediction;
import com.steve.formulaforecast.service.raceweekends.RaceWeekendPersistenceService;
import com.steve.formulaforecast.service.raceweekends.model.RaceWeekend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.InstantSource;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.steve.formulaforecast.service.prediction.PredictionException.INVALID_PICKS;
import static com.steve.formulaforecast.service.prediction.PredictionException.PREDICTIONS_LOCKED;
import static com.steve.formulaforecast.service.prediction.PredictionException.PREDICTION_TYPE_NOT_FOUND;
import static com.steve.formulaforecast.service.prediction.PredictionException.RACE_WEEKEND_NOT_FOUND;

@Service
public class PredictionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PredictionService.class);

    private final PredictionPersistenceService predictionPersistenceService;
    private final PredictionTypeService predictionTypeService;
    private final RaceWeekendPersistenceService raceWeekendPersistenceService;
    private final InstantSource instantSource;

    public PredictionService(
            PredictionPersistenceService predictionPersistenceService,
            PredictionTypeService predictionTypeService,
            RaceWeekendPersistenceService raceWeekendPersistenceService,
            InstantSource instantSource) {
        this.predictionPersistenceService = predictionPersistenceService;
        this.predictionTypeService = predictionTypeService;
        this.raceWeekendPersistenceService = raceWeekendPersistenceService;
        this.instantSource = instantSource;
    }

    public void makeDriverPrediction(DriverPrediction driverPrediction) {
        validatePredictionsOpen(driverPrediction.getRaceWeekendUid());
        PredictionTypeDetail predictionTypeDetail = predictionTypeService.getPredictionTypeByUid(driverPrediction.getPredictionTypeUid())
                .orElseThrow(() -> new PredictionException(PREDICTION_TYPE_NOT_FOUND));
        if (!isOnePickPerPosition(driverPrediction.getRankedDriverPredictions(), predictionTypeDetail.getSelectionCount())) {
            LOGGER.info("Rejecting picks for prediction type=[{}] that need one distinct driver in each of [{}] positions",
                    predictionTypeDetail.getPredictionType(), predictionTypeDetail.getSelectionCount());
            throw new PredictionException(INVALID_PICKS);
        }
        LOGGER.info("Making [{}] prediction for user team=[{}] for raceWeekend=[{}]",
                predictionTypeDetail.getPredictionType(), driverPrediction.getUserTeamUid(), driverPrediction.getRaceWeekendUid());
        predictionPersistenceService.saveDriverPrediction(driverPrediction);
    }

    /**
     * True when there is exactly one pick for each position from 1 to the selection count, and no driver is picked twice.
     */
    static boolean isOnePickPerPosition(List<RankedDriverPrediction> picks, int selectionCount) {
        List<Integer> ranks = picks.stream().map(RankedDriverPrediction::getRank).sorted().toList();
        long distinctDrivers = picks.stream().map(RankedDriverPrediction::getDriverUid).distinct().count();
        boolean noMissingDrivers = picks.stream().allMatch(pick -> pick.getDriverUid() != null);
        return noMissingDrivers
                && distinctDrivers == picks.size()
                && ranks.equals(IntStream.rangeClosed(1, selectionCount).boxed().toList());
    }

    private void validatePredictionsOpen(UUID raceWeekendUid) {
        RaceWeekend raceWeekend = raceWeekendPersistenceService.getRaceWeekend(raceWeekendUid)
                .orElseThrow(() -> new PredictionException(RACE_WEEKEND_NOT_FOUND));
        Instant now = instantSource.instant();
        if (!now.isBefore(raceWeekend.getPredictionsLockAt())) {
            LOGGER.info("Rejecting prediction for raceWeekend=[{}], picks locked at [{}]", raceWeekendUid, raceWeekend.getPredictionsLockAt());
            throw new PredictionException(PREDICTIONS_LOCKED);
        }
    }

    public Map<UUID, DriverPrediction> getDriverPredictionsForRaceWeekendForTeam(UUID raceWeekendUid, UUID userTeamUid) {
        return predictionTypeService.getAllPredictionTypes().stream()
                .collect(Collectors.toMap(
                        PredictionTypeDetail::getPredictionTypeUid,
                        predictionTypeDetail -> {
                            UUID predictionTypeUid = predictionTypeDetail.getPredictionTypeUid();
                            return predictionPersistenceService.selectRaceWeekendDriverPredictionForType(raceWeekendUid, userTeamUid, predictionTypeUid)
                                    .orElse(new DriverPrediction(predictionTypeUid, userTeamUid, raceWeekendUid, List.of()));
                        }
                ));
    }

}
