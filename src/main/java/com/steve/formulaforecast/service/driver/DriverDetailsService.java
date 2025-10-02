package com.steve.formulaforecast.service.driver;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class DriverDetailsService {

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
}
