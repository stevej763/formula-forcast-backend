package com.steve.formulaforecast.persistence;

import com.steve.formulaforecast.persistence.entity.raceweekend.PracticeSessionEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;

import java.util.UUID;
import java.util.stream.Stream;

public interface RaceWeekendPracticeRepository extends Repository<PracticeSessionEntity, Long> {

    @Query("""
            SELECT practice_session_uid, session_date, practice_session_number
            FROM practice_session
            WHERE practice_session.race_weekend_id = (SELECT race_weekend.id FROM race_weekend WHERE race_weekend.race_weekend_uid = :raceWeekendUid)
            """)
    Stream<PracticeSessionEntity> selectPracticeSessionsForRaceWeekend(UUID raceWeekendUid);
}
