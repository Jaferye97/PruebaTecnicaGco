package com.yeimer.ecommerceapi.infra.adapters.out.db.mapper;

import com.yeimer.ecommerceapi.domain.pojos.Movement;
import com.yeimer.ecommerceapi.infra.adapters.out.db.entities.MovementEntity;

public class MovementEntityMapper {
    public static Movement toDomain(MovementEntity entity) {
        return Movement.builder()
                .id(entity.getId())
                .idProduct(entity.getProduct().getId())
                .amount(entity.getAmount())
                .type(entity.getType())
                .description(entity.getDescription())
                .date(entity.getDate())
                .build();
    }

    public static MovementEntity toEntity(Movement domain) {
        MovementEntity entity = new MovementEntity();
        if(domain.getId() > 0)
            entity.setId(domain.getId());
        entity.setAmount(domain.getAmount());
        entity.setType(domain.getType());
        entity.setDescription(domain.getDescription());
        entity.setDate(domain.getDate());
        return entity;
    }
}
