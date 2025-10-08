package com.steve.formulaforecast.persistence;

import com.steve.formulaforecast.persistence.entity.constructor.ConstructorDetailEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;

import java.util.UUID;
import java.util.stream.Stream;

public interface ConstructorRepository extends Repository<ConstructorDetailEntity, Long> {

    @Query("""
            SELECT
                constructor_uid,
                team_name,
                base
                FROM constructor
            """)
    Stream<ConstructorDetailEntity> selectAllConstructors();

    @Modifying
    @Query("""
            INSERT INTO constructor(
                constructor_uid,
                team_name,
                base)
            VALUES
                (:constructorUid, :teamName, :base)
            """)
    int insertConstructor(UUID constructorUid, String teamName, String base);
}
