package com.steve.formulaforecast.service.constructor;

import com.steve.formulaforecast.api.constructor.ConstructorCreationDetails;
import com.steve.formulaforecast.service.constructor.model.ConstructorDetail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ConstructorService {

    ConstructorPersistenceService constructorPersistenceService;

    public ConstructorService(ConstructorPersistenceService constructorPersistenceService) {
        this.constructorPersistenceService = constructorPersistenceService;
    }

    @Transactional
    public List<ConstructorDetail> getAllConstructors() {
        return constructorPersistenceService.selectAllConstructors();
    }

    public void createConstructor(ConstructorCreationDetails constructorCreationDetails) {
        UUID constructorUid = UUID.randomUUID();
        constructorPersistenceService.insertConstructor(constructorUid, constructorCreationDetails);
    }
}
