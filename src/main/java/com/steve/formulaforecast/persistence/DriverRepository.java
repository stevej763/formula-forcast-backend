package com.steve.formulaforecast.persistence;

import com.neovisionaries.i18n.CountryCode;
import com.steve.formulaforecast.persistence.entity.driver.DriverEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface DriverRepository extends Repository<DriverEntity, Long> {

    @Query("""
            SELECT driver_uid, first_name, last_name, nickname, date_of_birth, nationality
            FROM driver
            """)
    Stream<DriverEntity> selectAllDrivers();

    @Query("""
            SELECT driver_uid, first_name, last_name, nickname, date_of_birth, nationality
            FROM driver
            WHERE driver_uid = :driverUid
            """)
    Optional<DriverEntity> selectDriver(UUID driverUid);

    @Modifying
    @Query("""
            INSERT INTO driver(
                driver_uid,
                first_name,
                last_name,
                nickname,
                nationality,
                date_of_birth)
            VALUES
                (:driverUid, :firstName, :lastName, :nickname, :nationality, :dateOfBirth)
            """)
    int insertDriver(UUID driverUid, String firstName, String lastName, String nickname, CountryCode nationality, LocalDate dateOfBirth);
}
