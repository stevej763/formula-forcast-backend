package com.steve.formulaforecast.persistence;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;

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
}
