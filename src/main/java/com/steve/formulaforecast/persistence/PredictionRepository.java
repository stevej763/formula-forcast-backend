package com.steve.formulaforecast.persistence;

import com.steve.formulaforecast.persistence.entity.PredictionDetailEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;

import java.time.Instant;
import java.util.UUID;

public interface PredictionRepository extends Repository<PredictionDetailEntity, Long> {

    @Modifying
    @Query("""
            INSERT INTO prediction(
                prediction_uid,
                prediction_type_id,
                race_weekend_id,
                user_team_id,
                created_at)
                VALUES
                (
                    :predictionUid,
                    (SELECT id FROM prediction_type WHERE prediction_type_uid = :predictionTypeUid),
                    (SELECT id FROM race_weekend WHERE race_weekend_uid = :raceWeekendUid),
                    (SELECT id FROM user_team WHERE team_uid = :userTeamUid),
                    :createdAt
                )
                ON CONFLICT DO NOTHING
            """)
    int insertFastestLapPrediction(UUID predictionUid, UUID predictionTypeUid, UUID raceWeekendUid, UUID userTeamUid, Instant createdAt);

    @Modifying
    @Query("""
            INSERT INTO prediction_choice(
                prediction_choice_uid,
                prediction_id,
                driver_id,
                is_ranked,
                created_at)
                VALUES
                (
                    :predictionChoiceUid,
                    (SELECT id FROM prediction WHERE prediction_uid = :predictionUid),
                    (SELECT id FROM driver WHERE driver_uid = :driverUid),
                    FALSE,
                    :createdAt
            )
            """)
    void insertFastestLapPredictionChoice(UUID predictionChoiceUid, UUID predictionUid, UUID driverUid, Instant createdAt);
}
