package com.steve.formulaforecast.persistence;

import com.steve.formulaforecast.persistence.entity.raceweekend.RaceWeekendEntity;
import com.steve.formulaforecast.service.raceweekends.model.RaceWeekendState;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface RaceWeekendRepository extends Repository<RaceWeekendEntity, Long> {

    @Query("""
            SELECT
                race_weekend_uid,
                round_number,
                race_weekend_end_date,
                race_weekend_start_date,
                race_name,
                race_location,
                status,
                event_time
            FROM race_weekend
            JOIN race_weekend_current_status status ON race_weekend.id = status.race_weekend_id
            ORDER BY race_weekend_start_date
            """)
    Stream<RaceWeekendEntity> selectAllRaceWeekends();

    @Query("""
            SELECT
                race_weekend_uid,
                round_number,
                race_weekend_end_date,
                race_weekend_start_date,
                race_name,
                race_location,
                status,
                event_time
            FROM race_weekend
            JOIN race_weekend_current_status status ON race_weekend.id = status.race_weekend_id
            JOIN championship_season season ON race_weekend.championship_season_id = season.id
            WHERE season.championship_season_uid = :seasonUid
            ORDER BY round_number
            """)
    Stream<RaceWeekendEntity> selectAllRaceWeekendsForSeason(UUID seasonUid);

    @Query("""
            SELECT
                race_weekend_uid,
                round_number,
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
                round_number,
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
                round_number,
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
                round_number,
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

    @Query("""
            SELECT
                race_weekend_uid,
                round_number,
                race_weekend_end_date,
                race_weekend_start_date,
                race_name,
                race_location,
                status,
                event_time
            FROM race_weekend
            JOIN race_weekend_current_status status ON race_weekend.id = status.race_weekend_id
            WHERE status <> 'COMPLETE'
            ORDER BY race_weekend_start_date
            """)
    Stream<RaceWeekendEntity> selectIncompleteRaceWeekends();

    /**
     * Inserts the weekend as the last round of its season. Call {@link #renumberSeason} afterwards so rounds follow start dates.
     */
    @Modifying
    @Query("""
        INSERT INTO public.race_weekend(
            race_weekend_uid,
            race_weekend_start_date,
            race_weekend_end_date,
            race_name,
            race_location,
            championship_season_id,
            round_number)
        SELECT
            :raceWeekendUid,
            :raceWeekendStartDate,
            :raceWeekendEndDate,
            :raceName,
            :raceLocation,
            season.id,
            (SELECT COALESCE(MAX(round_number), 0) + 1 FROM race_weekend WHERE race_weekend.championship_season_id = season.id)
        FROM championship_season season
        WHERE season.championship_season_uid = :championshipSeasonUid
    """)
    void insertRaceWeekend(UUID raceWeekendUid, LocalDate raceWeekendStartDate, LocalDate raceWeekendEndDate, String raceName, String raceLocation, UUID championshipSeasonUid);

    /**
     * Numbers a season's rounds in start date order. The season and round unique constraint is deferred, so rounds can swap in one statement.
     */
    @Modifying
    @Query("""
        UPDATE race_weekend
        SET round_number = ordered.round_number
        FROM (
            SELECT race_weekend.id, ROW_NUMBER() OVER (ORDER BY race_weekend_start_date, race_weekend.id) AS round_number
            FROM race_weekend
            JOIN championship_season season ON race_weekend.championship_season_id = season.id
            WHERE season.championship_season_uid = :championshipSeasonUid
        ) ordered
        WHERE race_weekend.id = ordered.id
        AND race_weekend.round_number <> ordered.round_number
    """)
    void renumberSeason(UUID championshipSeasonUid);

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
