package com.steve.formulaforecast.service.prediction;

import com.steve.formulaforecast.persistence.PredictionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.InstantSource;
import java.util.UUID;

@Service
public class PredicationPersistenceService {

    private final PredictionRepository predictionRepository;
    private final InstantSource instantSource;

    public PredicationPersistenceService(PredictionRepository predictionRepository, InstantSource instantSource) {
        this.predictionRepository = predictionRepository;
        this.instantSource = instantSource;
    }

    @Transactional
    public void saveFastestLapPrediction(FastestLapPrediction fastestLapPrediction) {
        UUID predictionUid = UUID.randomUUID();
        UUID predictionChoiceUid = UUID.randomUUID();
        Instant createdAt = instantSource.instant();
        predictionRepository.insertFastestLapPrediction(
                predictionUid,
                fastestLapPrediction.getPredictionTypeUid(),
                fastestLapPrediction.getRaceWeekendUid(),
                fastestLapPrediction.getUserTeamUid(),
                createdAt);
        predictionRepository.insertFastestLapPredictionChoice(
                predictionChoiceUid,
                predictionUid,
                fastestLapPrediction.getDriverUid(),
                createdAt);
    }
}
