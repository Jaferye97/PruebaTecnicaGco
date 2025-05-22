package com.yeimer.ecommerceapi.application.ports;

import com.yeimer.ecommerceapi.domain.pojos.Movement;

import java.util.List;
import java.util.Optional;

public interface MovementRepositoryPort {
    Movement save(Movement movement);
    Movement update(Movement movement);
    Optional<Movement> findById(Long id);
    List<Movement> getAll();
}
