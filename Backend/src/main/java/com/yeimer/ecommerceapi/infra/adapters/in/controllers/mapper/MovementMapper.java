package com.yeimer.ecommerceapi.infra.adapters.in.controllers.mapper;

import com.yeimer.ecommerceapi.domain.enums.TypeMovement;
import com.yeimer.ecommerceapi.domain.pojos.Movement;
import com.yeimer.ecommerceapi.infra.adapters.in.controllers.dto.movementDto.MovementDTO;


public class MovementMapper {
    public static MovementDTO toMovementDto(Movement movement) {
        return MovementDTO.builder()
                .id(movement.getId())
                .idProduct(movement.getIdProduct())
                .type(String.valueOf(movement.getType()))
                .amount(movement.getAmount())
                .description(movement.getDescription())
                .date(movement.getDate())
                .build();
    }

    public static Movement toAppObject(MovementDTO movementDTO) {
        return Movement.builder()
                .id(movementDTO.getId())
                .idProduct(movementDTO.getIdProduct())
                .type(TypeMovement.valueOf(movementDTO.getType()))
                .amount(movementDTO.getAmount())
                .description(movementDTO.getDescription())
                .date(movementDTO.getDate())
                .build();
    }
}
