package com.yeimer.ecommerceapi.infra.adapters.in.controllers;

import com.yeimer.ecommerceapi.application.useCases.movement.CreateMovement;
import com.yeimer.ecommerceapi.domain.pojos.Movement;
import com.yeimer.ecommerceapi.infra.adapters.in.controllers.dto.ErrorResponse;
import com.yeimer.ecommerceapi.infra.adapters.in.controllers.dto.movementDto.MovementDTO;
import com.yeimer.ecommerceapi.infra.adapters.in.controllers.mapper.MovementMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Movement", description = "Operations related to inventory movements")
public class MovementController {

    private final CreateMovement  createMovement;

    public MovementController(CreateMovement createMovement) {
        this.createMovement = createMovement;
    }

    @Operation(
            summary = "Create a new inventory movement",
            description = "Creates a movement of type ENTRADA (in) or SALIDA (out) and updates the stock accordingly."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Movement created successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MovementDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Validation failed",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Product not found or insufficient stock",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    )
    @PostMapping("/movement")
    public ResponseEntity<MovementDTO> createMovement(
            @RequestBody @Valid MovementDTO movementDTO
    ){
        Movement movement = MovementMapper.toAppObject(movementDTO);
        Movement createdMovement = createMovement.save(movement);

        return ResponseEntity.ok(MovementMapper.toMovementDto(createdMovement));
    }
}
