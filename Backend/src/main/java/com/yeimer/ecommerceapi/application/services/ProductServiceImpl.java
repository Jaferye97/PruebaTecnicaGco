package com.yeimer.ecommerceapi.application.services;

import com.yeimer.ecommerceapi.application.ports.ProductRepositoryPort;
import com.yeimer.ecommerceapi.application.useCases.Product.*;
import com.yeimer.ecommerceapi.domain.pojos.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements CreateProduct, UpdateProduct, GetProductById, GetProductAll, ToggleIsActiveProduct {

    private final ProductRepositoryPort productRepositoryPort;

    public ProductServiceImpl(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    public Product save(Product product) {
        return productRepositoryPort.save(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepositoryPort.findById(id);
    }

    @Override
    public Product update(Product product) {
        return productRepositoryPort.update(product);
    }

    @Override
    public List<Product> getAll() {
        return productRepositoryPort.getAll();
    }

    @Override
    public Product toggleIsActiveById(Long id) {
        return productRepositoryPort.toggleIsActiveById(id);
    }
}
