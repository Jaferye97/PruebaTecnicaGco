package com.yeimer.ecommerceapi.application.services;

import com.yeimer.ecommerceapi.application.ports.MovementRepositoryPort;
import com.yeimer.ecommerceapi.application.ports.ProductRepositoryPort;
import com.yeimer.ecommerceapi.domain.enums.TypeMovement;
import com.yeimer.ecommerceapi.domain.pojos.Movement;
import com.yeimer.ecommerceapi.domain.pojos.Product;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MovementServiceImplTest {
    @Mock private MovementRepositoryPort movementRepositoryPort;
    @Mock private ProductRepositoryPort productRepositoryPort;
    @Mock
    private ProductServiceImpl productService;

    @InjectMocks
    private MovementServiceImpl movementService;

    @Test
    void save_ShouldThrowException_WhenProductNotFound() {
        // Arrange
        Movement movement = Movement.builder()
                .idProduct(1)
                .build();

        when(productRepositoryPort.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            movementService.save(movement);
        });

        assertEquals("Product not found with id: 1", exception.getMessage());
    }

    @Test
    void save_ShouldAddStock_WhenMovementIsEntrada() {
        // Arrange
        Movement movement = Movement.builder()
                .idProduct(1)
                .amount(10)
                .type(TypeMovement.ENTRADA)
                .build();


        Product product = Product.builder()
                .id(1)
                .stock(5)
                .build();

        when(productRepositoryPort.findById(1L)).thenReturn(Optional.of(product));
        when(movementRepositoryPort.save(movement)).thenReturn(movement);
        when(productService.update(any())).thenReturn(product);

        // Act
        Movement result = movementService.save(movement);

        // Assert
        assertEquals(movement, result);
        assertEquals(15, product.getStock());
        verify(productService).update(product);
        verify(productService).checkStock(product);
    }

    @Test
    void save_ShouldSubtractStock_WhenMovementIsSalida() {
        // Arrange
        Movement movement = Movement.builder()
                .idProduct(1)
                .amount(3)
                .type(TypeMovement.SALIDA)
                .build();

        Product product = Product.builder()
                .id(1)
                .stock(10)
                .build();

        when(productRepositoryPort.findById(1L)).thenReturn(Optional.of(product));
        when(movementRepositoryPort.save(movement)).thenReturn(movement);
        when(productService.update(any())).thenReturn(product);

        // Act
        Movement result = movementService.save(movement);

        // Assert
        assertEquals(movement, result);
        assertEquals(7, product.getStock());
        verify(productService).update(product);
        verify(productService).checkStock(product);
    }

    @Test
    void save_ShouldThrowException_WhenStockIsInsufficient() {
        // Arrange
        Movement movement = Movement.builder()
                .idProduct(1)
                .amount(15)
                .type(TypeMovement.SALIDA)
                .build();

        Product product = Product.builder()
                .id(1)
                .stock(10)
                .build();

        when(productRepositoryPort.findById(1L)).thenReturn(Optional.of(product));

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            movementService.save(movement);
        });

        assertEquals("Only 10 units left in stock", exception.getMessage());
        verify(productService, never()).update(any());
    }

    @Test
    void update_ShouldReturnUpdatedMovement() {
        // Arrange
        Movement movement = Movement.builder()
                .id(1)
                .build();

        when(movementRepositoryPort.update(movement)).thenReturn(movement);

        // Act
        Movement result = movementService.update(movement);

        // Assert
        assertEquals(movement, result);
        verify(movementRepositoryPort).update(movement);
    }

    @Test
    void getAll_ShouldReturnListOfMovements() {
        // Arrange
        List<Movement> mockMovements = Arrays.asList(Movement.builder().build(), Movement.builder().build());
        when(movementRepositoryPort.getAll()).thenReturn(mockMovements);

        // Act
        List<Movement> result = movementService.getAll();

        // Assert
        assertEquals(2, result.size());
        verify(movementRepositoryPort).getAll();
    }

    @Test
    void findById_ShouldReturnMovement_WhenExists() {
        // Arrange
        Movement movement = Movement.builder()
                .id(1)
                .build();

        when(movementRepositoryPort.findById(1L)).thenReturn(Optional.of(movement));

        // Act
        Optional<Movement> result = movementService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(movementRepositoryPort).findById(1L);
    }
}
