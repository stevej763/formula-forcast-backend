package com.steve.formulaforecast.persistence;

import com.steve.formulaforecast.persistence.entity.raceweekend.RaceWeekendEntity;
import com.steve.formulaforecast.service.raceweekends.model.RaceWeekendState;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
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
                event_time,
                season.championship_season_uid
            FROM race_weekend
            JOIN race_weekend_current_status status ON race_weekend.id = status.race_weekend_id
            JOIN championship_season season ON race_weekend.championship_season_id = season.id
            WHERE season.championship_season_uid = :seasonUid
            """)
    Stream<RaceWeekendEntity> selectAllRaceWeekendsForSeason(UUID seasonUid);

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
    Optional<RaceWeekendEntity> selectLiveRaceWeekend();

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
            WHERE status = 'UPCOMING'
            ORDER BY race_weekend_start_date
            LIMIT 1
            """)
    Optional<RaceWeekendEntity> selectNextRaceWeekend();

    @Modifying
    @Query("""
        INSERT INTO public.race_weekend_current_status(
            race_weekend_id,
            event_time,
            status)
        VALUES((SELECT id FROM race_weekend WHERE race_weekend.race_weekend_uid = :raceWeekendUid), :eventTime, :raceWeekendState)
        ON CONFLICT ON CONSTRAINT uk_race_weekend_current_status_race_weekend_id_status
        DO UPDATE SET (status, event_time) = (:raceWeekendState, :eventTime)
        WHERE race_weekend_current_status.race_weekend_id = (SELECT id FROM race_weekend WHERE race_weekend.race_weekend_uid = :raceWeekendUid)
    """)
    void updateRaceWeekendCurrentStatus(UUID raceWeekendUid, Instant eventTime, RaceWeekendState raceWeekendState);

    @Modifying
    @Query("""
        INSERT INTO public.race_weekend_status_history(race_weekend_id, status, event_time)
        VALUES(
            (SELECT id FROM race_weekend WHERE race_weekend.race_weekend_uid = :raceWeekendUid),
            :raceWeekendState,
            :eventTime)
    """)
    void updateRaceWeekendStatusHistory(UUID raceWeekendUid, RaceWeekendState raceWeekendState, Instant eventTime);
}
