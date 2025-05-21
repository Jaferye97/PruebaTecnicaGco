package com.yeimer.ecommerceapi.application.useCases.Product;

import com.yeimer.ecommerceapi.domain.pojos.Product;

import java.util.List;

public interface FindByNameContaining {
    List<Product> findByNameContaining(String name);
}
