package com.steve.formulaforecast.api.driver;

import java.util.UUID;

public record DriverConstructorRequest(UUID driverUid, UUID constructorUid) {
}
