package com.yeimer.ecommerceapi.infra.adapters.out.db.repositories;

import com.yeimer.ecommerceapi.application.ports.ProductRepositoryPort;
import com.yeimer.ecommerceapi.domain.pojos.Product;
import com.yeimer.ecommerceapi.infra.adapters.out.db.entities.ProductEntity;
import com.yeimer.ecommerceapi.infra.adapters.out.db.mapper.ProductEntityMapper;
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
    public List<Product> findByCategoryContaining(String category) {
        return springProductRepository.findByCategoryContaining(category)
                .stream()
                .map(ProductEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findByNameContaining(String name) {
        return springProductRepository.findByNameContaining(name)
                .stream()
                .map(ProductEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findByCodeContaining(String code) {
        return springProductRepository.findByCodeContaining(code)
                .stream()
                .map(ProductEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Product update(Product product) {
        ProductEntity entity = ProductEntityMapper.toEntity(product);
        ProductEntity updatedEntity = springProductRepository.save(entity);
        return ProductEntityMapper.toDomain(updatedEntity);
    }

    @Override
    public List<Product> findByCode(String code) {
        return springProductRepository.findByCode(code)
                .stream()
                .map(ProductEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById (long id){ return springProductRepository.existsById(id); }
}
