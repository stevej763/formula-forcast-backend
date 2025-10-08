package com.steve.formulaforecast.persistence.entity.constructor;

import java.util.UUID;

public record ConstructorDetailEntity(UUID constructorUid, String teamName, String base) {
}
