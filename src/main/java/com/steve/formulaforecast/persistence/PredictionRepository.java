package com.steve.formulaforecast.persistence;

import com.steve.formulaforecast.persistence.entity.prediction.PredictionDetailEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

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
    int insertPrediction(UUID predictionUid, UUID predictionTypeUid, UUID raceWeekendUid, UUID userTeamUid, Instant createdAt);

    @Query("""
        SELECT
            prediction.prediction_uid,
            prediction.points
        FROM
            prediction
        WHERE
            prediction.prediction_type_id = (SELECT id FROM prediction_type WHERE prediction_type_uid = :predictionTypeUid)
            AND prediction.race_weekend_id = (SELECT id FROM race_weekend WHERE race_weekend_uid = :raceWeekendUid)
            AND prediction.user_team_id = (SELECT id FROM user_team WHERE team_uid = :userTeamUid)
        """)
    Optional<PredictionDetailEntity> selectExistingPrediction(UUID predictionTypeUid, UUID raceWeekendUid, UUID userTeamUid);

    @Modifying
    @Query("""
        DELETE FROM prediction_choice
        WHERE prediction_id = (SELECT id FROM prediction WHERE prediction_uid = :predictionUid)
    """)
    void deletePredictionChoices(UUID predictionUid);

    @Modifying
    @Query("""
            INSERT INTO prediction_choice(
                prediction_choice_uid,
                prediction_id,
                driver_id,
                rank,
                created_at)
                VALUES
                (
                    :predictionChoiceUid,
                    (SELECT id FROM prediction WHERE prediction_uid = :predictionUid),
                    (SELECT id FROM driver WHERE driver_uid = :driverUid),
                    :rank,
                    :createdAt
            )
            """)
    void insertPredictionChoice(UUID predictionChoiceUid, UUID predictionUid, UUID driverUid, int rank, Instant createdAt);

    @Query("""
        SELECT
            pc.prediction_choice_uid,
            d.driver_uid,
            pc.rank
        FROM
            prediction_choice pc
            JOIN prediction p ON pc.prediction_id = p.id
            JOIN driver d ON pc.driver_id = d.id
        WHERE
            p.prediction_uid = :predictionUid
        ORDER BY
            pc.rank ASC
        """)
    Stream<DriverPredictionEntity> selectPredictionChoices(UUID predictionUid);
}
