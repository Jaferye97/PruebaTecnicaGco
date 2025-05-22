package com.yeimer.ecommerceapi.infra.adapters.in.controllers.dto.movementDto;

import com.yeimer.ecommerceapi.domain.enums.TypeMovement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class MovementDTO {
    private int id;
    private int idProduct;
    private TypeMovement type;
    private int amount;
    private LocalDateTime date;
    private String description;
}
