package com.steve.formulaforecast.service.prediction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PredictionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PredictionService.class);
    private static final List<PredictionType> TOP_THREE_PREDICTIONS = List.of(PredictionType.QUALIFYING_TOP_THREE, PredictionType.RACE_TOP_THREE);


    private final PredictionPersistenceService predictionPersistenceService;
    private final PredictionTypeService predictionTypeService;


    public PredictionService(PredictionPersistenceService predictionPersistenceService, PredictionTypeService predictionTypeService) {
        this.predictionPersistenceService = predictionPersistenceService;
        this.predictionTypeService = predictionTypeService;
    }

    public void makeDriverPrediction(DriverPrediction driverPrediction) {
        PredictionTypeDetail predictionTypeDetail = predictionTypeService.getPredictionTypeByUid(driverPrediction.getPredictionTypeUid())
                .orElseThrow(() -> new IllegalArgumentException("Invalid prediction type UID: " + driverPrediction.getPredictionTypeUid()));

        if (driverPrediction.getRankedDriverPredictions().size() == 3) {
            if (!TOP_THREE_PREDICTIONS.contains(predictionTypeDetail.getPredictionType())) {
                throw new IllegalArgumentException("Prediction type " + predictionTypeDetail.getPredictionType() + " does not support top three selections.");
            }
            predictionPersistenceService.saveRankedDriverPrediction(driverPrediction);
            return;
        }
        if (driverPrediction.getRankedDriverPredictions().size() == 1) {
            LOGGER.info("Making driver prediction for user team=[{}] for raceWeekend=[{}]",
                    driverPrediction.getUserTeamUid(), driverPrediction.getRaceWeekendUid());
            SingleDriverPredication singleDriverPredication = new SingleDriverPredication(
                    driverPrediction.getPredictionTypeUid(),
                    driverPrediction.getRankedDriverPredictions().getFirst().getDriverUid(),
                    driverPrediction.getRaceWeekendUid(),
                    driverPrediction.getUserTeamUid());
            predictionPersistenceService.saveSingleDriverPrediction(singleDriverPredication);
        }
    }

    public Map<UUID, DriverPrediction> getDriverPredictionForRaceWeekendForTeam(UUID raceWeekendUid, UUID userTeamUid) {
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
