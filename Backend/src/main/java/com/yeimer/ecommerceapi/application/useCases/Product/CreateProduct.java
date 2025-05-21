package com.yeimer.ecommerceapi.application.useCases.Product;

import com.yeimer.ecommerceapi.domain.pojos.Product;

public interface CreateProduct {
    Product save(Product product);
}
