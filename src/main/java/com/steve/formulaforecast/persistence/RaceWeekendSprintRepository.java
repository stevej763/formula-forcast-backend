package com.steve.formulaforecast.persistence;

import com.steve.formulaforecast.persistence.entity.raceweekend.SprintSessionEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

public interface RaceWeekendSprintRepository extends Repository<SprintSessionEntity, Long> {

    @Query("""
            SELECT sprint_session_uid, session_date
            FROM sprint_session
            WHERE sprint_session.race_weekend_id = (SELECT race_weekend.id FROM race_weekend WHERE race_weekend.race_weekend_uid = :raceWeekendUid)
            """)
    Optional<SprintSessionEntity> selectSprintSessionForRaceWeekend(UUID raceWeekendUid);
}
