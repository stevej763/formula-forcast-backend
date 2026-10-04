package com.steve.formulaforecast.persistence;

import com.steve.formulaforecast.persistence.entity.raceweekend.RaceSessionEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface RaceWeekendRaceRepository extends Repository<RaceSessionEntity, Long> {

    @Query("""
            SELECT race_session_uid, session_date
            FROM race_session
            WHERE race_session.race_weekend_id = (SELECT race_weekend.id FROM race_weekend WHERE race_weekend.race_weekend_uid = :raceWeekendUid)
            """)
    Optional<RaceSessionEntity> selectRaceSessionForRaceWeekend(UUID raceWeekendUid);

    @Modifying
    @Query("""
            INSERT INTO race_session (race_session_uid, race_weekend_id, session_date)
            VALUES (:sessionUid, (SELECT race_weekend.id FROM race_weekend WHERE race_weekend.race_weekend_uid = :raceWeekendUid), :sessionDate)
            """)
    void insertRaceSession(UUID sessionUid, UUID raceWeekendUid, LocalDate sessionDate);
}
