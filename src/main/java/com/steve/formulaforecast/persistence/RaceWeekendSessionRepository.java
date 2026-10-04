package com.steve.formulaforecast.persistence;

import com.steve.formulaforecast.persistence.entity.raceweekend.RaceWeekendSessionEntity;
import com.steve.formulaforecast.service.raceweekends.model.SessionType;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Stream;

public interface RaceWeekendSessionRepository extends Repository<RaceWeekendSessionEntity, Long> {

    @Query("""
            SELECT race_weekend_session_uid, session_type, starts_at
            FROM race_weekend_session
            WHERE race_weekend_session.race_weekend_id = (SELECT race_weekend.id FROM race_weekend WHERE race_weekend.race_weekend_uid = :raceWeekendUid)
            ORDER BY starts_at
            """)
    Stream<RaceWeekendSessionEntity> selectSessionsForRaceWeekend(UUID raceWeekendUid);

    @Modifying
    @Query("""
            INSERT INTO race_weekend_session (race_weekend_session_uid, race_weekend_id, session_type, starts_at)
            VALUES (:sessionUid, (SELECT race_weekend.id FROM race_weekend WHERE race_weekend.race_weekend_uid = :raceWeekendUid), :sessionType, :startsAt)
            """)
    void insertSession(UUID sessionUid, UUID raceWeekendUid, SessionType sessionType, Instant startsAt);
}
