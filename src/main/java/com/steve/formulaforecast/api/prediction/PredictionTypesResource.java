package com.steve.formulaforecast.api.prediction;

import com.steve.formulaforecast.api.prediction.model.prediction.PredictionTypesResponse;
import com.steve.formulaforecast.api.prediction.model.prediction.PreditionTypeDetailDto;
import com.steve.formulaforecast.service.prediction.PredictionTypeDetail;
import com.steve.formulaforecast.service.prediction.PredictionTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/predictions/types", produces = APPLICATION_JSON_VALUE)
public class PredictionTypesResource {

    private final PredictionTypeService predictionTypeService;

    PredictionTypesResource(PredictionTypeService predictionTypeService) {
        this.predictionTypeService = predictionTypeService;
    }

    @GetMapping
    public ResponseEntity<PredictionTypesResponse> getPredictionTypes() {
        List<PreditionTypeDetailDto> allPredictionTypes = predictionTypeService.getAllPredictionTypes().stream()
                .map(this::toDto)
                .toList();
        return ResponseEntity.ok(new PredictionTypesResponse(allPredictionTypes));
    }

    private PreditionTypeDetailDto toDto(PredictionTypeDetail predictionTypeDetail) {
        return new PreditionTypeDetailDto(
                predictionTypeDetail.getPredictionTypeUid(),
                predictionTypeDetail.getPredictionType().name(),
                predictionTypeDetail.getPredictionSelectionType().name(),
                predictionTypeDetail.getDescription(),
                predictionTypeDetail.getCreatedAt()
        );
    }
}
