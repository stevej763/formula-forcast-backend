package com.steve.formulaforecast.service.driver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DriverDetailsService {

    Logger LOGGER = LoggerFactory.getLogger(DriverDetailsService.class);

    private final DriverDetailsPersistenceService driverDetailsPersistenceService;


    public DriverDetailsService(DriverDetailsPersistenceService driverDetailsPersistenceService) {
        this.driverDetailsPersistenceService = driverDetailsPersistenceService;
    }

    @Transactional
    public List<DriverDetails> getDrivers() {
        return driverDetailsPersistenceService.selectAllDrivers();
    }

    @Transactional
    public Optional<DriverDetails> getDriver(UUID driverUid) {
        return driverDetailsPersistenceService.getDriverByUid(driverUid);
    }

    @Transactional
    public List<DriverDetails> getAllDriversForCurrentSeason() {
        return null;
    }

    @Transactional
    public void createDriver(DriverCreationDetails driverDetails) {
        UUID driverUid = UUID.randomUUID();
        driverDetailsPersistenceService.insertDriver(driverUid, driverDetails);
    }

    @Transactional
    public void updateDriverConstructor(UUID driverUid, UUID constructorUid) {
        LOGGER.info("Updating driver {} to constructor {}", driverUid, constructorUid);
        driverDetailsPersistenceService.updateDriverConstructor(driverUid, constructorUid);
    }
}
