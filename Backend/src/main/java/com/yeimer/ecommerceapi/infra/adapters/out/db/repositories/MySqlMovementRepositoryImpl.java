package com.yeimer.ecommerceapi.infra.adapters.out.db.repositories;

import com.yeimer.ecommerceapi.application.ports.MovementRepositoryPort;
import com.yeimer.ecommerceapi.domain.pojos.Movement;
import com.yeimer.ecommerceapi.infra.adapters.out.db.entities.MovementEntity;
import com.yeimer.ecommerceapi.infra.adapters.out.db.entities.ProductEntity;
import com.yeimer.ecommerceapi.infra.adapters.out.db.mapper.MovementEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.persistence.EntityManager;

@Repository
public class MySqlMovementRepositoryImpl implements MovementRepositoryPort {

    private final SpringMovementRepository springMovementRepository;

    @Autowired
    private EntityManager entityManager;

    public MySqlMovementRepositoryImpl(SpringMovementRepository springMovementRepository) {
        this.springMovementRepository = springMovementRepository;
    }

    @Override
    public Movement save(Movement movement) {
        ProductEntity product = entityManager.getReference(ProductEntity.class, movement.getIdProduct());
        MovementEntity entity = MovementEntityMapper.toEntity(movement);
        entity.setProduct(product);
        MovementEntity savedEntity = springMovementRepository.save(entity);
        return MovementEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Movement update(Movement movement) {
        MovementEntity entity = MovementEntityMapper.toEntity(movement);
        MovementEntity updatedEntity = springMovementRepository.save(entity);
        return MovementEntityMapper.toDomain(updatedEntity);
    }

    @Override
    public Optional<Movement> findById(Long id) {
        return springMovementRepository.findById(id).map(MovementEntityMapper::toDomain);
    }

    @Override
    public List<Movement> getAll() {
        return springMovementRepository.findAll()
                .stream()
                .map(MovementEntityMapper::toDomain)
                .collect(Collectors.toList());
    }
}
