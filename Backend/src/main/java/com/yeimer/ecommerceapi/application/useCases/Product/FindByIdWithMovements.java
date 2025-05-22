package com.yeimer.ecommerceapi.application.useCases.Product;

import com.yeimer.ecommerceapi.domain.pojos.ProductWithMovement;

public interface FindByIdWithMovements {
    ProductWithMovement findByIdWithMovements(Long id);
}
