package com.yeimer.ecommerceapi.infra.adapters.out.db.repositories;

import com.yeimer.ecommerceapi.infra.adapters.out.db.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByCategoryContaining(String category);

    List<ProductEntity> findByNameContaining(String name);

    List<ProductEntity> findByCodeContaining(String code);

    List<ProductEntity> findByCode(String code);
}
