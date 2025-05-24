package com.yeimer.ecommerceapi.infra.adapters.in.controllers.dto.movementDto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

@Data
@Getter
@Setter
@Builder
public class MovementDTO {
    private int id;

    @NotNull(message = "Product ID must not be null")
    @Min(value = 1, message = "Product ID must be greater than 0")
    private int idProduct;

    @NotNull(message = "Type must not be null")
    @Pattern(regexp = "ENTRADA|SALIDA", message = "Type must be 'ENTRADA' or 'SALIDA'")
    private String type;

    @NotNull(message = "Amount must not be null")
    @Min(value = 1, message = "Amount must be greater than 0")
    private int amount;

    @NotNull(message = "Date must not be null")
    private LocalDateTime date;

    @NotBlank(message = "Description must not be blank")
    private String description;
}
