package com.steve.formulaforecast.api.constructor;

import com.steve.formulaforecast.api.constructor.model.constructor.ConstructorCreationRequest;
import com.steve.formulaforecast.api.constructor.model.constructor.ConstructorDetailDto;
import com.steve.formulaforecast.api.constructor.model.constructor.ConstructorDetailResponse;
import com.steve.formulaforecast.service.constructor.ConstructorService;
import com.steve.formulaforecast.service.constructor.model.ConstructorDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/constructor", produces = APPLICATION_JSON_VALUE)
public class ConstructorResource {


    private final ConstructorService constructorService;

    ConstructorResource(ConstructorService constructorService) {
        this.constructorService = constructorService;
    }

    @GetMapping("/all")
    public ResponseEntity<ConstructorDetailResponse> getAllConstructors() {
        List<ConstructorDetailDto> allConstructors = constructorService.getAllConstructors().stream()
                .map(this::toDto)
                .toList();
        return ResponseEntity.ok(new ConstructorDetailResponse(allConstructors));
    }

    private ConstructorDetailDto toDto(ConstructorDetail constructorDetail) {
        return new ConstructorDetailDto(constructorDetail.getConstructorUid(), constructorDetail.getTeamName(), constructorDetail.getBase());
    }

    @PostMapping("/create")
    public void createConstructor(@RequestBody ConstructorCreationRequest constructorCreationRequest) {
        ConstructorCreationDetails constructorCreationDetails = new ConstructorCreationDetails(
                constructorCreationRequest.teamName(),
                constructorCreationRequest.base());
        constructorService.createConstructor(constructorCreationDetails);
    }
}
