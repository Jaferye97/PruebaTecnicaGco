package com.yeimer.ecommerceapi.application.useCases.movement;

import com.yeimer.ecommerceapi.domain.pojos.Movement;

import java.util.Optional;

public interface GetMovementById {
    Optional<Movement> findById(Long id);
}
