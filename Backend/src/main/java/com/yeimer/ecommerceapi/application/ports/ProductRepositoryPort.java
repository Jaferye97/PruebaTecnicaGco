package com.yeimer.ecommerceapi.application.ports;

import com.yeimer.ecommerceapi.domain.pojos.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryPort {
    Product save(Product product);
    Optional<Product> findById(Long id);
    List<Product> getAll();
    Product update(Product product);
    List<Product> findByCategoryContaining(String category);
    List<Product> findByNameContaining(String name);
    List<Product> findByCodeContaining(String code);
    List<Product> findByCode(String code);
    boolean existsById (long id);
}
