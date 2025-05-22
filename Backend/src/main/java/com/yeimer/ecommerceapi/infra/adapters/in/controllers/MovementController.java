package com.yeimer.ecommerceapi.infra.adapters.in.controllers;

import com.yeimer.ecommerceapi.application.useCases.movement.CreateMovement;
import com.yeimer.ecommerceapi.domain.pojos.Movement;
import com.yeimer.ecommerceapi.infra.adapters.in.controllers.dto.movementDto.MovementDTO;
import com.yeimer.ecommerceapi.infra.adapters.in.controllers.mapper.MovementMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovementController {

    private final CreateMovement  createMovement;

    public MovementController(CreateMovement createMovement) {
        this.createMovement = createMovement;
    }

    @PostMapping("/movement")
    public ResponseEntity<MovementDTO> createMovement(
            @RequestBody MovementDTO movementDTO
    ){
        Movement movement = MovementMapper.toAppObject(movementDTO);
        Movement createdMovement = createMovement.save(movement);

        return ResponseEntity.ok(MovementMapper.toMovementDto(createdMovement));
    }
}
