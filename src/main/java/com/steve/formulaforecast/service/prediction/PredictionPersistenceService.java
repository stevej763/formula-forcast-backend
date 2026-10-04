package com.steve.formulaforecast.service.prediction;

import com.steve.formulaforecast.api.prediction.model.prediction.RankedDriverPrediction;
import com.steve.formulaforecast.persistence.DriverPredictionEntity;
import com.steve.formulaforecast.persistence.PredictionRepository;
import com.steve.formulaforecast.persistence.entity.prediction.PredictionDetailEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.InstantSource;
import java.util.List;
import java.util.Optional;
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

    /**
     * Saves a team's picks for a prediction type, replacing any picks it already made for that weekend.
     */
    @Transactional
    public void saveDriverPrediction(DriverPrediction driverPrediction) {
        Instant createdAt = instantSource.instant();
        predictionRepository.insertPrediction(
                UUID.randomUUID(),
                driverPrediction.getPredictionTypeUid(),
                driverPrediction.getRaceWeekendUid(),
                driverPrediction.getUserTeamUid(),
                createdAt);
        PredictionDetailEntity prediction = predictionRepository.selectExistingPrediction(
                        driverPrediction.getPredictionTypeUid(),
                        driverPrediction.getRaceWeekendUid(),
                        driverPrediction.getUserTeamUid())
                .orElseThrow();
        LOGGER.info("Saving picks for user team=[{}] for raceWeekend=[{}] on prediction=[{}]",
                driverPrediction.getUserTeamUid(), driverPrediction.getRaceWeekendUid(), prediction.predictionUid());

        // Replace rather than update the picks, so drivers can swap positions without breaking the one driver per prediction constraint
        predictionRepository.deletePredictionChoices(prediction.predictionUid());
        driverPrediction.getRankedDriverPredictions().forEach(rankedDriverPrediction ->
                predictionRepository.insertPredictionChoice(
                        UUID.randomUUID(),
                        prediction.predictionUid(),
                        rankedDriverPrediction.getDriverUid(),
                        rankedDriverPrediction.getRank(),
                        createdAt));
    }

    @Transactional
    public Optional<DriverPrediction> selectRaceWeekendDriverPredictionForType(UUID raceWeekendUid, UUID userTeamUid, UUID predictionTypeUid) {
        return predictionRepository.selectExistingPrediction(predictionTypeUid, raceWeekendUid, userTeamUid)
                .map(prediction -> {
                    List<RankedDriverPrediction> rankedDriverPredictions = predictionRepository.selectPredictionChoices(prediction.predictionUid())
                            .map(this::toModel)
                            .toList();
                    return new DriverPrediction(predictionTypeUid, userTeamUid, raceWeekendUid, rankedDriverPredictions, prediction.points());
                });
    }

    private RankedDriverPrediction toModel(DriverPredictionEntity driverPredictionEntity) {
        return new RankedDriverPrediction(driverPredictionEntity.driverUid(), driverPredictionEntity.rank());
    }
}
