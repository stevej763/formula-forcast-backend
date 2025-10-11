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
            SELECT 
                driver_uid, 
                first_name, 
                last_name, 
                nickname, 
                date_of_birth, 
                nationality, 
                c.constructor_uid, 
                c.team_name
            FROM driver
            LEFT JOIN (
                SELECT DISTINCT ON (driver_id) *
                FROM driver_constructor_mapping
                ORDER BY driver_id, season_id DESC
            ) dcm ON dcm.driver_id = driver.id
            LEFT JOIN constructor c ON c.id = dcm.constructor_id
            """)
    Stream<DriverEntity> selectAllDrivers();

    @Query("""
            SELECT
                driver_uid,
                first_name, 
                last_name, 
                nickname, 
                date_of_birth, 
                nationality, 
                c.constructor_uid, 
                c.team_name
            FROM driver
            LEFT JOIN driver_constructor_mapping dcm ON dcm.driver_id = driver.id
            LEFT JOIN constructor c ON c.id = dcm.constructor_id
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

    @Modifying
    @Query("""
        INSERT INTO driver_constructor_mapping(
            driver_id,
            constructor_id,
            season_id
        ) VALUES (
            (SELECT id FROM driver WHERE driver_uid = :driverUid),
            (SELECT id FROM constructor WHERE constructor_uid = :constructorUid),
            (SELECT id FROM championship_season WHERE championship_season_uid = :currentSeason)
        ) ON CONFLICT ON CONSTRAINT uk_driver_constructor_mapping_unique_season_driver DO UPDATE SET
            constructor_id = (SELECT id FROM constructor WHERE constructor_uid = :constructorUid)
        """)
    void updateDriverConstructor(UUID driverUid, UUID constructorUid, UUID currentSeason);
}
