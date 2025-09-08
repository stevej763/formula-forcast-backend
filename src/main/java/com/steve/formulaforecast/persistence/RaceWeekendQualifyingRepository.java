package com.steve.formulaforecast.persistence;

import com.steve.formulaforecast.persistence.entity.raceweekend.QualifyingSessionEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

public interface RaceWeekendQualifyingRepository extends Repository<QualifyingSessionEntity, Long> {

    @Query("""
            SELECT qualifying_session_uid, session_date
            FROM qualifying_session
            WHERE qualifying_session.race_weekend_id = (SELECT race_weekend.id FROM race_weekend WHERE race_weekend.race_weekend_uid = :raceWeekendUid)
            """)
    Optional<QualifyingSessionEntity> selectQualifyingSessionForRaceWeekend(UUID raceWeekendUid);
}
