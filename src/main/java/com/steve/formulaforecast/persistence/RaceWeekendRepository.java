package com.steve.formulaforecast.persistence;

import com.steve.formulaforecast.persistence.entity.raceweekend.RaceWeekendEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface RaceWeekendRepository extends Repository<RaceWeekendEntity, Long> {

    @Query("""
            SELECT
                race_weekend_uid,
                race_weekend_end_date,
                race_weekend_start_date,
                race_name,
                race_location,
                status,
                event_time
            FROM race_weekend
            JOIN race_weekend_current_status status ON race_weekend.id = status.race_weekend_id
            """)
    Stream<RaceWeekendEntity> selectAllRaceWeekends();

    @Query("""
            SELECT
                race_weekend_uid,
                race_weekend_end_date,
                race_weekend_start_date,
                race_name,
                race_location,
                status,
                event_time
            FROM race_weekend
            JOIN race_weekend_current_status status ON race_weekend.id = status.race_weekend_id
            WHERE race_weekend_uid = :raceWeekendUid
            """)
    Optional<RaceWeekendEntity> selectRaceWeekends(UUID raceWeekendUid);

    @Query("""
            SELECT
                race_weekend_uid,
                race_weekend_end_date,
                race_weekend_start_date,
                race_name,
                race_location,
                status,
                event_time
            FROM race_weekend
            JOIN race_weekend_current_status status ON race_weekend.id = status.race_weekend_id
            WHERE status = 'LIVE'
            """)
    Optional<RaceWeekendEntity> selectCurrentRaceWeekend();

    @Query("""
            SELECT
                race_weekend_uid,
                race_weekend_end_date,
                race_weekend_start_date,
                race_name,
                race_location,
                status,
                event_time
            FROM race_weekend
            JOIN race_weekend_current_status status ON race_weekend.id = status.race_weekend_id
            WHERE status = 'RACE_WEEK'
            """)
    Optional<RaceWeekendEntity> selectNextRaceWeekend();
}
