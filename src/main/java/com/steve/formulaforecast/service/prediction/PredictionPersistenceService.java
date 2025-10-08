package com.steve.formulaforecast.service.prediction;

import com.steve.formulaforecast.persistence.PredictionRepository;
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
    public void saveFastestLapPrediction(FastestLapPrediction fastestLapPrediction) {
        UUID predictionUid = UUID.randomUUID();
        UUID predictionChoiceUid = UUID.randomUUID();
        Instant createdAt = instantSource.instant();
        int inserted = predictionRepository.insertFastestLapPrediction(
                predictionUid,
                fastestLapPrediction.getPredictionTypeUid(),
                fastestLapPrediction.getRaceWeekendUid(),
                fastestLapPrediction.getUserTeamUid(),
                createdAt);
        if (inserted == 0) {
            LOGGER.info("Prediction for user team=[{}] for raceWeekend=[{}] already exists, skipping insert",
                    fastestLapPrediction.getUserTeamUid(), fastestLapPrediction.getRaceWeekendUid());
        } else {
            predictionRepository.insertFastestLapPredictionChoice(
                    predictionChoiceUid,
                    predictionUid,
                    fastestLapPrediction.getDriverUid(),
                    createdAt);
        }
    }
}
