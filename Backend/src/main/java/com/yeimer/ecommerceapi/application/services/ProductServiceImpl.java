package com.yeimer.ecommerceapi.application.services;

import com.yeimer.ecommerceapi.application.ports.ProductRepositoryPort;
import com.yeimer.ecommerceapi.application.useCases.Product.*;
import com.yeimer.ecommerceapi.domain.pojos.Product;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements
        CreateProduct,
        UpdateProduct,
        GetProductById,
        GetProductAll,
        ToggleIsActiveProduct,
        FindByCategoryContaining,
        FindByCodeContaining,
        FindByNameContaining {

    private final ProductRepositoryPort productRepositoryPort;

    public ProductServiceImpl(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    public Product save(Product product) {
        List<Product> productWithSameCode = productRepositoryPort.findByCode(product.getCode());

        if((long) productWithSameCode.size() > 0){
            throw new EntityExistsException("Product with same code already exists");
        }

        return productRepositoryPort.save(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepositoryPort.findById(id);
    }

    @Override
    public Product update(Product product) {
        if(productRepositoryPort.existsById(product.getId())){
            throw new EntityNotFoundException("Product not found with id: " + product.getId());
        }

        List<Product> productWithSameCode = productRepositoryPort.findByCode(product.getCode());

        if(productWithSameCode.stream().anyMatch(p -> p.getId() != product.getId())){
            throw new EntityExistsException("Product with same code already exists");
        }

        return productRepositoryPort.update(product);
    }

    @Override
    public List<Product> getAll() {
        return productRepositoryPort.getAll();
    }

    @Override
    public Product toggleIsActiveById(Long id) {
        Optional<Product> product = productRepositoryPort.findById(id);

        if(product.isEmpty()){
            throw new EntityNotFoundException("Product not found with id: " + id);
        }

        product.get().setActive(!product.get().isActive());
        return productRepositoryPort.update(product.get());
    }

    @Override
    public List<Product> findByCategoryContaining(String category) {
        return productRepositoryPort.findByCategoryContaining(category);
    }

    @Override
    public List<Product> findByCodeContaining(String code) {
        return productRepositoryPort.findByCodeContaining(code);
    }

    @Override
    public List<Product> findByNameContaining(String name) {
        return productRepositoryPort.findByNameContaining(name);
    }
}
