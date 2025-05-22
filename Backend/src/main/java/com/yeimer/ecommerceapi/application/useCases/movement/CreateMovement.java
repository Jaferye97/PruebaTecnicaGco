package com.yeimer.ecommerceapi.application.useCases.movement;

import com.yeimer.ecommerceapi.domain.pojos.Movement;

public interface CreateMovement {
    Movement save(Movement movement);
}
