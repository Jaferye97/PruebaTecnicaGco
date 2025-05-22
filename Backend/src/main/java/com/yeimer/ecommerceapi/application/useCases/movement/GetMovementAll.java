package com.yeimer.ecommerceapi.application.useCases.movement;

import com.yeimer.ecommerceapi.domain.pojos.Movement;

import java.util.List;

public interface GetMovementAll {
    List<Movement> getAll();
}
