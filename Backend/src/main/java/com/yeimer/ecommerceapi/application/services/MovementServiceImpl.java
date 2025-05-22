package com.yeimer.ecommerceapi.application.services;

import com.yeimer.ecommerceapi.application.ports.MovementRepositoryPort;
import com.yeimer.ecommerceapi.application.ports.ProductRepositoryPort;
import com.yeimer.ecommerceapi.application.useCases.movement.CreateMovement;
import com.yeimer.ecommerceapi.application.useCases.movement.GetMovementAll;
import com.yeimer.ecommerceapi.application.useCases.movement.GetMovementById;
import com.yeimer.ecommerceapi.application.useCases.movement.UpdateMovement;
import com.yeimer.ecommerceapi.domain.enums.TypeMovement;
import com.yeimer.ecommerceapi.domain.pojos.Movement;
import com.yeimer.ecommerceapi.domain.pojos.Product;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.Optional;

@Service
public class MovementServiceImpl implements CreateMovement, UpdateMovement, GetMovementById, GetMovementAll {

    private final MovementRepositoryPort movementRepositoryPort;
    private final ProductRepositoryPort productRepositoryPort;

    private final ProductServiceImpl productServiceImpl;

    public MovementServiceImpl(
            MovementRepositoryPort movementRepositoryPort,
            ProductRepositoryPort productRepositoryPort,
            ProductServiceImpl productServiceImpl) {
        this.movementRepositoryPort = movementRepositoryPort;
        this.productRepositoryPort = productRepositoryPort;
        this.productServiceImpl = productServiceImpl;
    }

    @Override
    @Transactional
    public Movement save(Movement movement) {
        Optional<Product> product = productRepositoryPort.findById((long) movement.getIdProduct());

        if (product.isEmpty()) {
            throw new EntityNotFoundException("Product not found with id: " + movement.getIdProduct());
        }

        if (movement.getType() == TypeMovement.ENTRADA) {
            product.get().setStock(product.get().getStock() + movement.getAmount());
        } else {
            if (product.get().getStock() < movement.getAmount()) {
                throw new EntityNotFoundException("Only " + product.get().getStock() + " units left in stock");
            }

            product.get().setStock(product.get().getStock() - movement.getAmount());
        }

        Product update = productServiceImpl.update(product.get());
        return movementRepositoryPort.save(movement);
    }

    @Override
    public Movement update(Movement movement) {
        return movementRepositoryPort.update(movement);
    }

    @Override
    public List<Movement> getAll() {
        return movementRepositoryPort.getAll();
    }

    @Override
    public Optional<Movement> findById(Long id) {
        return movementRepositoryPort.findById(id);
    }
}
