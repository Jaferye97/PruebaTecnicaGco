package com.yeimer.ecommerceapi.domain.pojos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class ProductWithMovement{
    private Product product;
    private List<Movement> movements;
}
