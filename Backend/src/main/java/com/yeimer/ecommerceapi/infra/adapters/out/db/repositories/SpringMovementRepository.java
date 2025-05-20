package com.yeimer.ecommerceapi.infra.adapters.out.db.repositories;

import com.yeimer.ecommerceapi.infra.adapters.out.db.entities.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringMovementRepository extends JpaRepository<MovementEntity, Long> {
}
