package com.yeimer.ecommerceapi.domain.pojos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class Product {
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
