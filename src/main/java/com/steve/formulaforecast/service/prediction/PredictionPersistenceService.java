package com.steve.formulaforecast.service.prediction;

import com.steve.formulaforecast.persistence.PredictionRepository;
import com.steve.formulaforecast.persistence.entity.prediction.PredictionDetailEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.InstantSource;
import java.util.UUID;

@Service
public class PredictionPersistenceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PredictionPersistenceService.class);

    private final PredictionRepository predictionRepository;
    private final InstantSource instantSource;

    public PredictionPersistenceService(PredictionRepository predictionRepository, InstantSource instantSource) {
        this.predictionRepository = predictionRepository;
        this.instantSource = instantSource;
    }

    @Transactional
    public void saveDriverPrediction(DriverPrediction driverPrediction) {
        UUID predictionUid = UUID.randomUUID();
        UUID predictionChoiceUid = UUID.randomUUID();
        Instant createdAt = instantSource.instant();
        int inserted = predictionRepository.insertPrediction(
                predictionUid,
                driverPrediction.getPredictionTypeUid(),
                driverPrediction.getRaceWeekendUid(),
                driverPrediction.getUserTeamUid(),
                createdAt);
        if (inserted == 0) {
            LOGGER.info("Prediction for user team=[{}] for raceWeekend=[{}] for type=[{}] already exists, updating existing prediction",
                    driverPrediction.getUserTeamUid(), driverPrediction.getRaceWeekendUid(), driverPrediction.getPredictionTypeUid());
            predictionRepository.selectExistingPredictionForUpdate(
                    driverPrediction.getPredictionTypeUid(),
                    driverPrediction.getRaceWeekendUid(),
                    driverPrediction.getUserTeamUid()
            ).ifPresent(existingPrediction -> {
                LOGGER.info("Found existing prediction with predictionUid=[{}], updating prediction choice",
                        existingPrediction.predictionUid());
                updatePredictionChoice(driverPrediction, existingPrediction, createdAt);
            });
        } else {
            predictionRepository.insertPredictionChoice(
                    predictionChoiceUid,
                    predictionUid,
                    driverPrediction.getDriverUid(),
                    createdAt);
        }
    }

    private void updatePredictionChoice(DriverPrediction driverPrediction, PredictionDetailEntity existingPrediction, Instant createdAt) {
        int inserted = predictionRepository.updatePredictionChoice(
                existingPrediction.predictionUid(),
                driverPrediction.getDriverUid(),
                createdAt);
        LOGGER.info("Updated prediction choice, rows affected=[{}]", inserted);
    }
}
