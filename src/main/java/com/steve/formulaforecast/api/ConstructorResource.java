package com.steve.formulaforecast.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

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
        return ResponseEntity.ok(new ConstructorDetailResponse(Collections.emptyList()));
    }

    @PostMapping("/create")
    public void createConstructor(@RequestBody ConstructorCreationRequest constructorCreationRequest) {

    }
}
