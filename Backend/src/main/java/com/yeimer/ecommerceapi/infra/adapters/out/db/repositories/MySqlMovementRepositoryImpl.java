package com.yeimer.ecommerceapi.infra.adapters.out.db.repositories;

import com.yeimer.ecommerceapi.application.ports.MovementRepositoryPort;
import com.yeimer.ecommerceapi.domain.pojos.Movement;
import com.yeimer.ecommerceapi.infra.adapters.out.db.entities.MovementEntity;
import com.yeimer.ecommerceapi.infra.adapters.out.db.mapper.MovementEntityMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MySqlMovementRepositoryImpl implements MovementRepositoryPort {

    private final SpringMovementRepository springMovementRepository;

    public MySqlMovementRepositoryImpl(SpringMovementRepository springMovementRepository) {
        this.springMovementRepository = springMovementRepository;
    }

    @Override
    public Movement save(Movement movement) {
        MovementEntity entity = MovementEntityMapper.toEntity(movement);
        MovementEntity savedEntity = springMovementRepository.save(entity);
        return MovementEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Movement update(Movement movement) {
        if (!springMovementRepository.existsById((long) movement.getId())) {
            throw new EntityNotFoundException("Movement not found with id: " + movement.getId());
        }
        MovementEntity entity = MovementEntityMapper.toEntity(movement);
        MovementEntity updatedEntity = springMovementRepository.save(entity);
        return MovementEntityMapper.toDomain(updatedEntity);
    }

    @Override
    public Optional<Movement> findById(Long id) {
        return springMovementRepository.findById(id).map(MovementEntityMapper::toDomain);
    }

    @Override
    public List<Movement> findAll() {
        return springMovementRepository.findAll()
                .stream()
                .map(MovementEntityMapper::toDomain)
                .collect(Collectors.toList());
    }
}
