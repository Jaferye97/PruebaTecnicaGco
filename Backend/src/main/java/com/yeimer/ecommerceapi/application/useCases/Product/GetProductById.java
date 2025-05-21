package com.yeimer.ecommerceapi.application.useCases.Product;

import com.yeimer.ecommerceapi.domain.pojos.Product;

import java.util.Optional;

public interface GetProductById {
    Optional<Product> findById(Long id);
}
