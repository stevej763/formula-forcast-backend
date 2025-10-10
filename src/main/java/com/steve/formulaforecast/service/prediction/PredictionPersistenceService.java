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
import java.util.Objects;
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

    @Transactional
    public void saveSingleDriverPrediction(SingleDriverPredication driverPrediction) {
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
            predictionRepository.selectExistingPrediction(
                    driverPrediction.getPredictionTypeUid(),
                    driverPrediction.getRaceWeekendUid(),
                    driverPrediction.getUserTeamUid()
            ).ifPresent(existingPrediction -> {
                LOGGER.info("Found existing prediction with predictionUid=[{}], updating prediction choice",
                        existingPrediction.predictionUid());
                updatePredictionChoice(driverPrediction, existingPrediction, createdAt);
            });
        } else {
            predictionRepository.insertUnrankedPredictionChoice(
                    predictionChoiceUid,
                    predictionUid,
                    driverPrediction.getDriverUid(),
                    createdAt);
        }
    }

    private void updatePredictionChoice(SingleDriverPredication driverPrediction, PredictionDetailEntity existingPrediction, Instant createdAt) {
        int inserted = predictionRepository.updatePredictionChoice(
                existingPrediction.predictionUid(),
                driverPrediction.getDriverUid(),
                createdAt);
        LOGGER.info("Updated prediction choice, rows affected=[{}]", inserted);
    }

    public void saveRankedDriverPrediction(DriverPrediction driverPrediction) {
        UUID predictionUid = UUID.randomUUID();
        Instant createdAt = instantSource.instant();
        int inserted = predictionRepository.insertPrediction(
                predictionUid,
                driverPrediction.getPredictionTypeUid(),
                driverPrediction.getRaceWeekendUid(),
                driverPrediction.getUserTeamUid(),
                createdAt);
        if (inserted == 0) {
            LOGGER.info("Prediction for user team=[{}] for raceWeekend=[{}] for type=[{}] already exists, updating drivers and positions",
                    driverPrediction.getUserTeamUid(), driverPrediction.getRaceWeekendUid(), driverPrediction.getPredictionTypeUid());
            predictionRepository.selectExistingPrediction(
                    driverPrediction.getPredictionTypeUid(),
                    driverPrediction.getRaceWeekendUid(),
                    driverPrediction.getUserTeamUid()
            ).ifPresent(existingPrediction -> {
                LOGGER.info("Found existing prediction with predictionUid=[{}], updating ranked prediction choices",
                        existingPrediction.predictionUid());
                driverPrediction.getRankedDriverPredictions().forEach((rankedDriverPrediction) -> {
                    predictionRepository.updateRankedPredictionChoice(
                            existingPrediction.predictionUid(),
                            rankedDriverPrediction.getDriverUid(),
                            createdAt,
                            rankedDriverPrediction.getRank());
                });
            });
        } else {
            driverPrediction.getRankedDriverPredictions().forEach((rankedDriverPrediction) -> {
                UUID predictionChoiceUid = UUID.randomUUID();
                predictionRepository.insertRankedPredictionChoice(
                        predictionChoiceUid,
                        predictionUid,
                        rankedDriverPrediction.getDriverUid(),
                        createdAt,
                        rankedDriverPrediction.getRank());
            });
        }
    }

    @Transactional
    public Optional<DriverPrediction> selectRaceWeekendDriverPredictionForType(UUID raceWeekendUid, UUID userTeamUid, UUID predictionTypeUid) {
        return predictionRepository.selectExistingPrediction(predictionTypeUid, raceWeekendUid, userTeamUid)
                .map(existingPrediction -> {
                    LOGGER.info("Found existing prediction with predictionUid=[{}], fetching ranked prediction choices",
                            existingPrediction.predictionUid());
                    List<RankedDriverPrediction> rankedDriverPredictions = predictionRepository.selectDriverPredictionsForType(
                                    raceWeekendUid,
                                    userTeamUid,
                                    predictionTypeUid,
                                    existingPrediction.predictionUid())
                            .map(this::toModel)
                            .toList();
                    return new DriverPrediction(predictionTypeUid, userTeamUid, raceWeekendUid, rankedDriverPredictions);
                });
    }

    private RankedDriverPrediction toModel(DriverPredictionEntity driverPredictionEntity) {
        return new RankedDriverPrediction(
                driverPredictionEntity.driverUid(),
                Objects.isNull(driverPredictionEntity.rank()) ? 1 :driverPredictionEntity.rank().intValue());
    }
}
