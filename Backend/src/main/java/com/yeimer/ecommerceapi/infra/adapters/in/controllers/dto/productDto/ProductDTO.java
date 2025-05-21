package com.yeimer.ecommerceapi.infra.adapters.in.controllers.dto.productDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ProductDTO {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
    private String category;
    private String code;
    private LocalDateTime dateCreation;
    private boolean isActive;
}
