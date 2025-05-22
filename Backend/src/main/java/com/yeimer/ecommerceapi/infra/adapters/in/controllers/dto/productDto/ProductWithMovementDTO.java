package com.yeimer.ecommerceapi.infra.adapters.in.controllers.dto.productDto;

import com.yeimer.ecommerceapi.infra.adapters.in.controllers.dto.movementDto.MovementDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductWithMovementDTO {
    private ProductDTO productDTO;
    private List<MovementDTO> movementDTOList;
}
