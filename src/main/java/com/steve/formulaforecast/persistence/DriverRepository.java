package com.steve.formulaforecast.persistence;

import com.steve.formulaforecast.persistence.entity.driver.DriverEntity;
import com.steve.formulaforecast.persistence.entity.raceweekend.RaceWeekendEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface DriverRepository extends Repository<DriverEntity, Long> {

    @Query("""
            SELECT driver_uid, first_name, last_name, nickname
            FROM driver
            """)
    Stream<DriverEntity> selectAllDrivers();

    @Query("""
            SELECT driver_uid, first_name, last_name, nickname
            FROM race_weekend
            WHERE driver_uid = :driverUid
            """)
    Optional<DriverEntity> selectDriver(UUID driverUid);
}
