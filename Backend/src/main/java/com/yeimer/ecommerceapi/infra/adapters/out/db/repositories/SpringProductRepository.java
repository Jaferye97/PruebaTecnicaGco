package com.yeimer.ecommerceapi.infra.adapters.out.db.repositories;

import com.yeimer.ecommerceapi.infra.adapters.out.db.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringProductRepository extends JpaRepository<ProductEntity, Long> {
}
