package com.yeimer.ecommerceapi.domain.pojos;

import com.yeimer.ecommerceapi.domain.enums.TypeMovement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class Movement {
    private int id;
    private int idProduct;
    private TypeMovement type;
    private int amount;
    private LocalDateTime date;
    private String description;
}
