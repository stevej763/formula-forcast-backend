package com.steve.formulaforecast.persistence;

import com.steve.formulaforecast.persistence.entity.prediction.PredictionTypeEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface PredictionTypeRepository extends Repository<PredictionTypeEntity, Long> {

    @Query("""
            SELECT
                prediction_type_uid,
                prediction_type,
                description,
                prediction_selection_type,
                created_at
            FROM prediction_type
            ORDER BY id
            """)
    Stream<PredictionTypeEntity> selectAllEnabledPredictionTypes();

    @Query("""
            SELECT
                prediction_type_uid,
                prediction_type,
                description,
                prediction_selection_type,
                created_at
            FROM prediction_type
            WHERE prediction_type_uid = :predictionTypeUid
            """)
    Optional<PredictionTypeEntity> selectPredictionTypeByUid(UUID predictionTypeUid);
}
