package com.steve.formulaforecast.persistence.entity.result;

import java.util.UUID;

public record ResultChoiceEntity(UUID predictionTypeUid, UUID driverUid, int rank) {
}
