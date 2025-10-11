package com.steve.formulaforecast.service.driver;

import com.steve.formulaforecast.persistence.DriverRepository;
import com.steve.formulaforecast.persistence.entity.driver.DriverEntity;
import com.steve.formulaforecast.service.leaderboard.ChampionshipSeason;
import com.steve.formulaforecast.service.leaderboard.ChampionshipSeasonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DriverDetailsPersistenceService {

    private final DriverRepository driverRepository;
    private final ChampionshipSeasonService championshipSeasonService;

    public DriverDetailsPersistenceService(DriverRepository driverRepository, ChampionshipSeasonService championshipSeasonService) {
        this.driverRepository = driverRepository;
        this.championshipSeasonService = championshipSeasonService;
    }

    @Transactional
    public List<DriverDetails> selectAllDrivers() {
        return driverRepository.selectAllDrivers().map(this::toModel).toList();
    }

    @Transactional
    public List<DriverDetails> selectAllActiveDrivers() {
        return driverRepository.selectAllActiveDrivers().map(this::toModel).toList();
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
                driverEntity.nickname(),
                driverEntity.dateOfBirth(),
                driverEntity.constructorUid(),
                driverEntity.teamName());
    }

    @Transactional
    public void insertDriver(UUID driverUid, DriverCreationDetails driverDetails) {
        driverRepository.insertDriver(
                driverUid,
                driverDetails.getFirstName(),
                driverDetails.getLastName(),
                driverDetails.getNickname(),
                driverDetails.getNationality(),
                driverDetails.getDateOfBirth()
        );
    }

    public void updateDriverConstructor(UUID driverUid, UUID constructorUid) {
        ChampionshipSeason currentSeason = championshipSeasonService.getCurrentSeason();
        driverRepository.updateDriverConstructor(driverUid, constructorUid, currentSeason.getChampionshipSeasonUid());
    }
}
