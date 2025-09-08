package com.steve.formulaforecast.api;

import com.steve.formulaforecast.api.model.driver.DriverDetailResponse;
import com.steve.formulaforecast.api.model.driver.DriverDetailsResponse;
import com.steve.formulaforecast.service.driver.DriverDetails;
import com.steve.formulaforecast.service.driver.DriverDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/driver", produces = APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class DriverResource {

    private final DriverDetailsService driverDetailsService;

    DriverResource(DriverDetailsService driverDetailsService) {
        this.driverDetailsService = driverDetailsService;
    }

    @GetMapping("/all")
    public ResponseEntity<DriverDetailsResponse> getAllDrivers() {
        List<DriverDetailResponse> driverDetailResponses = driverDetailsService.getDrivers().stream().map(this::toDto).toList();
        return ResponseEntity.ok(new DriverDetailsResponse(driverDetailResponses));
    }

    @GetMapping("/{driverUid}")
    public ResponseEntity<DriverDetailResponse> getDriver(@PathVariable UUID driverUid) {
        Optional<DriverDetailResponse> driverDetailResponses = driverDetailsService.getDriver(driverUid).map(this::toDto);
        return ResponseEntity.of(driverDetailResponses);
    }

    private DriverDetailResponse toDto(DriverDetails driverDetails) {
        return new DriverDetailResponse(
                driverDetails.getDriverUid(),
                driverDetails.getFirstName(),
                driverDetails.getLastName(),
                driverDetails.getNickname(),
                driverDetails.getNationality());
    }
}
