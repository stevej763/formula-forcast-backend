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
    void insertUnrankedPredictionChoice(UUID predictionChoiceUid, UUID predictionUid, UUID driverUid, Instant createdAt);


    @Query("""
        SELECT
            prediction.prediction_uid
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
        UPDATE prediction_choice
        SET driver_id = (SELECT id FROM driver WHERE driver_uid = :driverUid),
            created_at = :createdAt
        WHERE prediction_id = (SELECT id FROM prediction WHERE prediction_uid = :predictionUid)
    """)
    int updatePredictionChoice(UUID predictionUid, UUID driverUid, Instant createdAt);

    @Modifying
    @Query("""
            INSERT INTO prediction_choice(
                prediction_choice_uid,
                prediction_id,
                driver_id,
                is_ranked,
                created_at,
                rank)
                VALUES
                (
                    :predictionChoiceUid,
                    (SELECT id FROM prediction WHERE prediction_uid = :predictionUid),
                    (SELECT id FROM driver WHERE driver_uid = :driverUid),
                    TRUE,
                    :createdAt,
                    :rank
            )
            """)
    void insertRankedPredictionChoice(UUID predictionChoiceUid, UUID predictionUid, UUID driverUid, Instant createdAt, int rank);

    @Modifying
    @Query("""
        UPDATE prediction_choice
        SET driver_id = (SELECT id FROM driver WHERE driver_uid = :driverUid),
            created_at = :createdAt
        WHERE prediction_id = (SELECT id FROM prediction WHERE prediction_uid = :predictionUid)
        AND rank = :rank
    """)
    void updateRankedPredictionChoice(UUID predictionUid, UUID driverUid, Instant createdAt, int rank);

    @Query("""
        SELECT
            pc.prediction_choice_uid,
            d.driver_uid,
            pc.is_ranked,
            pc.rank
        FROM
            prediction_choice pc
            JOIN prediction p ON pc.prediction_id = p.id
            JOIN driver d ON pc.driver_id = d.id
        WHERE
            p.prediction_uid = :predicationUid
            and p.prediction_type_id = (SELECT id FROM prediction_type WHERE prediction_type_uid = :predictionTypeUid)
            and p.user_team_id = (SELECT id FROM user_team WHERE team_uid = :userTeamUid)
            and p.race_weekend_id = (SELECT id FROM race_weekend WHERE race_weekend_uid = :raceWeekendUid)

        ORDER BY
            pc.rank ASC
        """)
    Stream<DriverPredictionEntity> selectDriverPredictionsForType(UUID raceWeekendUid, UUID userTeamUid, UUID predictionTypeUid, UUID predicationUid);
}
