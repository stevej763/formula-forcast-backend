package com.steve.formulaforecast.service.driver;

import com.steve.formulaforecast.persistence.DriverRepository;
import com.steve.formulaforecast.persistence.entity.driver.DriverEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Driver;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DriverDetailsPersistenceService {

    private final DriverRepository driverRepository;

    public DriverDetailsPersistenceService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Transactional
    public List<DriverDetails> selectAllDrivers() {
        return driverRepository.selectAllDrivers().map(this::toModel).toList();
    }

    @Transactional
    public Optional<DriverDetails> getDriverByUid(UUID driverUid) {
        return driverRepository.selectDriver(driverUid).map(this::toModel);
    }

    private DriverDetails toModel(DriverEntity driverEntity) {
        return new DriverDetails(
                driverEntity.driverUid(),
                driverEntity.firstName(),
                driverEntity.lastName(),
                driverEntity.nationality(),
                driverEntity.nickname());
    }
}
