package com.steve.formulaforecast.persistence;

import com.steve.formulaforecast.persistence.entity.result.PredictionChoiceForScoringEntity;
import com.steve.formulaforecast.persistence.entity.result.ResultChoiceEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Stream;

public interface PredictionResultRepository extends Repository<ResultChoiceEntity, Long> {

    @Modifying
    @Query("""
            INSERT INTO prediction_result (prediction_result_uid, race_weekend_id, prediction_type_id, created_at, updated_at)
            VALUES (
                :predictionResultUid,
                (SELECT id FROM race_weekend WHERE race_weekend_uid = :raceWeekendUid),
                (SELECT id FROM prediction_type WHERE prediction_type_uid = :predictionTypeUid),
                :now,
                :now)
            ON CONFLICT ON CONSTRAINT uk_prediction_result_race_weekend_prediction_type
            DO UPDATE SET updated_at = :now
            """)
    void upsertResult(UUID predictionResultUid, UUID raceWeekendUid, UUID predictionTypeUid, Instant now);

    @Modifying
    @Query("""
            DELETE FROM prediction_result_choice
            WHERE prediction_result_id = (
                SELECT prediction_result.id
                FROM prediction_result
                JOIN race_weekend ON prediction_result.race_weekend_id = race_weekend.id
                JOIN prediction_type ON prediction_result.prediction_type_id = prediction_type.id
                WHERE race_weekend.race_weekend_uid = :raceWeekendUid
                AND prediction_type.prediction_type_uid = :predictionTypeUid)
            """)
    void deleteResultChoices(UUID raceWeekendUid, UUID predictionTypeUid);

    @Modifying
    @Query("""
            INSERT INTO prediction_result_choice (prediction_result_id, driver_id, rank)
            VALUES (
                (SELECT prediction_result.id
                 FROM prediction_result
                 JOIN race_weekend ON prediction_result.race_weekend_id = race_weekend.id
                 JOIN prediction_type ON prediction_result.prediction_type_id = prediction_type.id
                 WHERE race_weekend.race_weekend_uid = :raceWeekendUid
                 AND prediction_type.prediction_type_uid = :predictionTypeUid),
                (SELECT id FROM driver WHERE driver_uid = :driverUid),
                :rank)
            """)
    void insertResultChoice(UUID raceWeekendUid, UUID predictionTypeUid, UUID driverUid, int rank);

    @Query("""
            SELECT
                prediction_type.prediction_type_uid,
                driver.driver_uid,
                prediction_result_choice.rank
            FROM prediction_result_choice
            JOIN prediction_result ON prediction_result_choice.prediction_result_id = prediction_result.id
            JOIN prediction_type ON prediction_result.prediction_type_id = prediction_type.id
            JOIN race_weekend ON prediction_result.race_weekend_id = race_weekend.id
            JOIN driver ON prediction_result_choice.driver_id = driver.id
            WHERE race_weekend.race_weekend_uid = :raceWeekendUid
            ORDER BY prediction_type.id, prediction_result_choice.rank, driver.last_name
            """)
    Stream<ResultChoiceEntity> selectResultChoices(UUID raceWeekendUid);

    @Query("""
            SELECT
                prediction.prediction_uid,
                driver.driver_uid,
                prediction_choice.rank
            FROM prediction
            JOIN prediction_choice ON prediction_choice.prediction_id = prediction.id
            JOIN driver ON prediction_choice.driver_id = driver.id
            WHERE prediction.race_weekend_id = (SELECT id FROM race_weekend WHERE race_weekend_uid = :raceWeekendUid)
            AND prediction.prediction_type_id = (SELECT id FROM prediction_type WHERE prediction_type_uid = :predictionTypeUid)
            """)
    Stream<PredictionChoiceForScoringEntity> selectPredictionChoicesForScoring(UUID raceWeekendUid, UUID predictionTypeUid);

    @Modifying
    @Query("""
            UPDATE prediction
            SET points = :points, scored_at = :scoredAt
            WHERE prediction_uid = :predictionUid
            """)
    void updatePredictionPoints(UUID predictionUid, int points, Instant scoredAt);
}
