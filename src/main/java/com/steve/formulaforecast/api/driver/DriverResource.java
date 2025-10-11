package com.steve.formulaforecast.api.driver;

import com.steve.formulaforecast.api.driver.model.driver.DriverCreationRequest;
import com.steve.formulaforecast.api.driver.model.driver.DriverDetailResponse;
import com.steve.formulaforecast.api.driver.model.driver.DriverDetailsResponse;
import com.steve.formulaforecast.service.driver.DriverCreationDetails;
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

    @GetMapping("/all/active")
    public ResponseEntity<DriverDetailsResponse> getAllActiveDrivers() {
        List<DriverDetailResponse> driverDetailResponses = driverDetailsService.getAllActiveDrivers().stream().map(this::toDto).toList();
        return ResponseEntity.ok(new DriverDetailsResponse(driverDetailResponses));
    }

    @PostMapping("/create")
    public void createDriver(@RequestBody DriverCreationRequest driverCreationRequest) {
        DriverCreationDetails driverDetails = new DriverCreationDetails(
                driverCreationRequest.firstName(),
                driverCreationRequest.lastName(),
                driverCreationRequest.nickname(),
                driverCreationRequest.nationality(),
                driverCreationRequest.dateOfBirth()
        );
        driverDetailsService.createDriver(driverDetails);
    }

    @PostMapping("/set-constructor")
    public void setDriverConstructor(@RequestBody DriverConstructorRequest driverConstructorRequest) {
        driverDetailsService.updateDriverConstructor(driverConstructorRequest.driverUid(), driverConstructorRequest.constructorUid());
    }

    @GetMapping("/current-season")
    public ResponseEntity<DriverDetailsResponse> getCurrentSeasonDrivers() {
        List<DriverDetailResponse> driverDetailResponses = driverDetailsService.getAllDriversForCurrentSeason().stream().map(this::toDto).toList();
        return ResponseEntity.ok(new DriverDetailsResponse(driverDetailResponses));
    }

    @GetMapping("/{driverUids}")
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
                driverDetails.getNationality(),
                driverDetails.getDateOfBirth(),
                driverDetails.getConstructorUid(),
                driverDetails.getTeamName());
    }
}
