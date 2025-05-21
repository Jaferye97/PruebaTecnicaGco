package com.yeimer.ecommerceapi.infra.adapters.out.db.repositories;

import com.yeimer.ecommerceapi.application.ports.ProductRepositoryPort;
import com.yeimer.ecommerceapi.domain.pojos.Product;
import com.yeimer.ecommerceapi.infra.adapters.out.db.entities.ProductEntity;
import com.yeimer.ecommerceapi.infra.adapters.out.db.mapper.ProductEntityMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MySqlProductRepositoryImpl implements ProductRepositoryPort {

    private final SpringProductRepository springProductRepository;

    public MySqlProductRepositoryImpl(SpringProductRepository springProductRepository) {
        this.springProductRepository = springProductRepository;
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = ProductEntityMapper.toEntity(product);
        ProductEntity savedEntity = springProductRepository.save(entity);
        return ProductEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return springProductRepository.findById(id).map(ProductEntityMapper::toDomain);
    }

    @Override
    public List<Product> getAll() {
        return springProductRepository.findAll()
                .stream()
                .map(ProductEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Product update(Product product) {
        if (!springProductRepository.existsById((long) product.getId())) {
            throw new EntityNotFoundException("Product not found with id: " + product.getId());
        }
        ProductEntity entity = ProductEntityMapper.toEntity(product);
        ProductEntity updatedEntity = springProductRepository.save(entity);
        return ProductEntityMapper.toDomain(updatedEntity);
    }

    @Override
    public Product toggleIsActiveById(Long id) {
        ProductEntity entity = springProductRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        entity.setActive(!entity.isActive());

        ProductEntity updatedEntity = springProductRepository.save(entity);
        return ProductEntityMapper.toDomain(updatedEntity);
    }
}
