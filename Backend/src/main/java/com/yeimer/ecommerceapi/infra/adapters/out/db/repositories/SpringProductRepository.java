package com.yeimer.ecommerceapi.infra.adapters.out.db.repositories;

import com.yeimer.ecommerceapi.infra.adapters.out.db.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByCategoryContaining(String category);

    List<ProductEntity> findByNameContaining(String name);

    List<ProductEntity> findByCodeContaining(String code);

    List<ProductEntity> findByCode(String code);

    @Query("SELECT p FROM ProductEntity p WHERE p.code LIKE %:code% AND p.isActive = :state")
    List<ProductEntity> findByCodeContainingAndIsActive(String code, boolean state);

    @Query("SELECT p FROM ProductEntity p LEFT JOIN FETCH p.movements WHERE p.id = :id")
    ProductEntity findByIdWithMovements(@Param("id") Long id);
}
