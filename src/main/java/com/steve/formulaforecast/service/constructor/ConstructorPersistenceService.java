package com.steve.formulaforecast.service.constructor;

import com.steve.formulaforecast.api.constructor.ConstructorCreationDetails;
import com.steve.formulaforecast.persistence.ConstructorRepository;
import com.steve.formulaforecast.persistence.entity.constructor.ConstructorDetailEntity;
import com.steve.formulaforecast.service.constructor.model.ConstructorDetail;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ConstructorPersistenceService {

    private final ConstructorRepository constructorRepository;

    public ConstructorPersistenceService(ConstructorRepository constructorRepository) {
        this.constructorRepository = constructorRepository;
    }

    public List<ConstructorDetail> selectAllConstructors() {
        return constructorRepository.selectAllConstructors().map(this::toModel).toList();
    }

    private ConstructorDetail toModel(ConstructorDetailEntity constructorDetailEntity) {
        return new ConstructorDetail(
                constructorDetailEntity.constructorUid(),
                constructorDetailEntity.teamName(),
                constructorDetailEntity.base()
        );
    }

    public void insertConstructor(UUID constructorUid, ConstructorCreationDetails constructorCreationDetails) {
        constructorRepository.insertConstructor(
                constructorUid,
                constructorCreationDetails.getTeamName(),
                constructorCreationDetails.getBase()
        );
    }
}
