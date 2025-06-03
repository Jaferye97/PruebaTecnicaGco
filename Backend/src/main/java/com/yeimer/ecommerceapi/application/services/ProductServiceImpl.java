package com.yeimer.ecommerceapi.application.services;

import com.yeimer.ecommerceapi.application.ports.ProductRepositoryPort;
import com.yeimer.ecommerceapi.application.services.observers.StockSubject;
import com.yeimer.ecommerceapi.application.useCases.Product.*;
import com.yeimer.ecommerceapi.domain.pojos.Product;
import com.yeimer.ecommerceapi.domain.pojos.ProductWithMovement;
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
        FindByCodeContainingAndActiveIsTrue,
        FindByCodeContaining,
        FindByNameContaining,
        FindByIdWithMovements
{

    private final ProductRepositoryPort productRepositoryPort;
    private final StockSubject stockSubject;


    public ProductServiceImpl(ProductRepositoryPort productRepositoryPort, StockSubject stockSubject) {
        this.productRepositoryPort = productRepositoryPort;
        this.stockSubject = stockSubject;
    }

    public void checkStock(Product product) {
        if (product.getStock() <= 20) {
            stockSubject.notifyLowStock(product);
        }
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
        if(!productRepositoryPort.existsById(id)){
            throw new EntityNotFoundException("Product not found with id: " + id);
        }

        return productRepositoryPort.findById(id);
    }

    @Override
    public ProductWithMovement findByIdWithMovements(Long id){
        if(!productRepositoryPort.existsById(id)){
            throw new EntityNotFoundException("Product not found with id: " + id);
        }

        return productRepositoryPort.findByIdWithMovements(id);
    }

    @Override
    public Product update(Product product) {
        if(!productRepositoryPort.existsById(product.getId())){
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
    public List<Product> findByCodeContainingAndActiveIsTrue(String code) {
        return productRepositoryPort.findByCodeContainingAndIsActive(code, true);
    }

    @Override
    public List<Product> findByNameContaining(String name) {
        return productRepositoryPort.findByNameContaining(name);
    }
}
