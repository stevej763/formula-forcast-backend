package com.steve.formulaforecast.api.constructor.model.constructor;

import java.util.UUID;

public record ConstructorDetailDto(UUID constructorUid, String teamName, String base) {
}
