package com.yeimer.ecommerceapi.infra.adapters.in.controllers.dto.productDto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotBlank(message = "Description must not be blank")
    private String description;

    @NotNull(message = "Price must not be null")
    @DecimalMin(value = "0.01", inclusive = true, message = "Price must be greater than 0")
    private BigDecimal price;

    @Min(value = 0, message = "Stock cannot be negative")
    private int stock;

    @NotBlank(message = "Category must not be blank")
    private String category;

    @NotBlank(message = "Code must not be blank")
    private String code;
    private LocalDateTime dateCreation;
    private boolean isActive;
}
