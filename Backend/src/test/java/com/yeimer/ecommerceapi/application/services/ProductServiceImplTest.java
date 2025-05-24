package com.yeimer.ecommerceapi.application.services;

import com.yeimer.ecommerceapi.application.ports.ProductRepositoryPort;
import com.yeimer.ecommerceapi.application.services.observers.StockSubject;
import com.yeimer.ecommerceapi.domain.pojos.Product;
import com.yeimer.ecommerceapi.domain.pojos.ProductWithMovement;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
    @Mock
    private ProductRepositoryPort productRepositoryPort;

    @Mock
    private StockSubject stockSubject;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void checkStock_ShouldNotify_WhenStockIsLow() {
        Product lowStockProduct = Product.builder()
                .stock(10)
                .build();

        productService.checkStock(lowStockProduct);

        verify(stockSubject, times(1)).notifyLowStock(lowStockProduct);
    }

    @Test
    void checkStock_ShouldNotNotify_WhenStockIsHigh() {
        Product highStockProduct = Product.builder()
                .stock(50)
                .build();

        productService.checkStock(highStockProduct);

        verify(stockSubject, never()).notifyLowStock(any());
    }

    @Test
    void save_ShouldReturnProduct_WhenCodeIsUnique() {
        // Arrange
        Product product = Product.builder()
                .code("P001")
                .build();

        when(productRepositoryPort.findByCode("P001")).thenReturn(Collections.emptyList());
        when(productRepositoryPort.save(product)).thenReturn(product);

        // Act
        Product result = productService.save(product);

        // Assert
        assertNotNull(result);
        assertEquals("P001", result.getCode());
        verify(productRepositoryPort, times(1)).save(product);
    }

    @Test
    void save_ShouldThrowEntityExistsException_WhenCodeAlreadyExists() {
        // Arrange
        Product product = Product.builder()
                .code("P001")
                .build();

        List<Product> existingProducts = List.of(Product.builder().build());
        when(productRepositoryPort.findByCode("P001")).thenReturn(existingProducts);

        // Act & Assert
        EntityExistsException exception = assertThrows(EntityExistsException.class, () -> {
            productService.save(product);
        });

        assertEquals("Product with same code already exists", exception.getMessage());
        verify(productRepositoryPort, never()).save(any());
    }

    @Test
    void findByIdWithMovements_ShouldReturn_WhenProductExists() {
        // Arrange
        Long productId = 1L;
        ProductWithMovement pwm = ProductWithMovement.builder().build();
        when(productRepositoryPort.existsById(productId)).thenReturn(true);
        when(productRepositoryPort.findByIdWithMovements(productId)).thenReturn(pwm);

        // Act
        ProductWithMovement result = productService.findByIdWithMovements(productId);

        // Assert
        assertNotNull(result);
        verify(productRepositoryPort).existsById(productId);
        verify(productRepositoryPort).findByIdWithMovements(productId);
    }

    @Test
    void findByIdWithMovements_ShouldThrow_WhenProductDoesNotExist() {
        // Arrange
        Long productId = 999L;
        when(productRepositoryPort.existsById(productId)).thenReturn(false);

        // Act & Assert
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            productService.findByIdWithMovements(productId);
        });

        assertEquals("Product not found with id: 999", ex.getMessage());
        verify(productRepositoryPort, never()).findByIdWithMovements(any());
    }

    @Test
    void update_ShouldThrowNotFound_WhenProductDoesNotExist() {
        // Arrange
        Product product = Product.builder().id(1).build();

        when(productRepositoryPort.existsById(1)).thenReturn(false);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            productService.update(product);
        });

        assertEquals("Product not found with id: 1", exception.getMessage());
        verify(productRepositoryPort, never()).update(any());
    }

    @Test
    void update_ShouldThrowExists_WhenSameCodeUsedByDifferentProduct() {
        // Arrange
        Product productToUpdate = Product.builder()
                .id(1)
                .code("CODE123")
                .build();


        Product otherProduct = Product.builder()
                .id(2)
                .code("CODE123")
                .build();

        when(productRepositoryPort.existsById(1L)).thenReturn(true);
        when(productRepositoryPort.findByCode("CODE123")).thenReturn(List.of(otherProduct));

        // Act & Assert
        EntityExistsException exception = assertThrows(EntityExistsException.class, () -> {
            productService.update(productToUpdate);
        });

        assertEquals("Product with same code already exists", exception.getMessage());
        verify(productRepositoryPort, never()).update(any());
    }

    @Test
    void update_ShouldSucceed_WhenProductExistsAndCodeIsUnique() {
        // Arrange
        Product product = Product.builder()
                .id(1)
                .code("CODE123")
                .build();

        when(productRepositoryPort.existsById(1)).thenReturn(true);
        when(productRepositoryPort.findByCode("CODE123")).thenReturn(List.of(product));
        when(productRepositoryPort.update(product)).thenReturn(product);

        // Act
        Product result = productService.update(product);

        // Assert
        assertNotNull(result);
        assertEquals("CODE123", result.getCode());
        verify(productRepositoryPort).update(product);
    }

    @Test
    void getAll_ShouldReturnAllProducts() {
        // Arrange
        Product product1 = Product.builder()
                .id(1)
                .code("P1")
                .build();

        Product product2 = Product.builder()
                .id(2)
                .code("P2")
                .build();

        List<Product> productList = List.of(product1, product2);

        when(productRepositoryPort.getAll()).thenReturn(productList);

        // Act
        List<Product> result = productService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("P1", result.get(0).getCode());
        verify(productRepositoryPort).getAll();
    }

    @Test
    void toggleIsActiveById_ShouldThrowException_WhenProductNotFound() {
        // Arrange
        Long id = 1L;
        when(productRepositoryPort.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            productService.toggleIsActiveById(id);
        });

        assertEquals("Product not found with id: 1", exception.getMessage());
        verify(productRepositoryPort, never()).update(any());
    }

    @Test
    void toggleIsActiveById_ShouldToggleStatusAndUpdate_WhenProductExists() {
        // Arrange
        int id = 1;

        Product product = Product.builder()
                .id(id)
                .isActive(true)
                .build();

        when(productRepositoryPort.findById((long) id)).thenReturn(Optional.of(product));
        when(productRepositoryPort.update(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Product result = productService.toggleIsActiveById((long) id);

        // Assert
        assertNotNull(result);
        assertFalse(result.isActive());
        verify(productRepositoryPort).update(product);
    }

    @Test
    void toggleIsActiveById_ShouldActivate_WhenProductIsInactive() {
        // Arrange
        int id = 2;
        Product product = Product.builder()
                .id(id)
                .isActive(false)
                .build();

        when(productRepositoryPort.findById((long) id)).thenReturn(Optional.of(product));
        when(productRepositoryPort.update(any())).thenAnswer(inv -> inv.getArgument(0));

        // Act
        Product result = productService.toggleIsActiveById((long) id);

        // Assert
        assertTrue(result.isActive());
        verify(productRepositoryPort).update(product);
    }

    @Test
    void findByCategoryContaining_ShouldReturnMatchingProducts() {
        // Arrange
        String category = "Electronics";
        List<Product> expectedProducts = List.of(Product.builder().build(), Product.builder().build());
        when(productRepositoryPort.findByCategoryContaining(category)).thenReturn(expectedProducts);

        // Act
        List<Product> result = productService.findByCategoryContaining(category);

        // Assert
        assertEquals(2, result.size());
        verify(productRepositoryPort).findByCategoryContaining(category);
    }

    @Test
    void findByCodeContaining_ShouldReturnMatchingProducts() {
        // Arrange
        String code = "ABC";
        List<Product> expectedProducts = List.of(Product.builder().build());
        when(productRepositoryPort.findByCodeContaining(code)).thenReturn(expectedProducts);

        // Act
        List<Product> result = productService.findByCodeContaining(code);

        // Assert
        assertEquals(1, result.size());
        verify(productRepositoryPort).findByCodeContaining(code);
    }

    @Test
    void findByNameContaining_ShouldReturnMatchingProducts() {
        // Arrange
        String name = "Laptop";
        List<Product> expectedProducts = List.of();
        when(productRepositoryPort.findByNameContaining(name)).thenReturn(expectedProducts);

        // Act
        List<Product> result = productService.findByNameContaining(name);

        // Assert
        assertTrue(result.isEmpty());
        verify(productRepositoryPort).findByNameContaining(name);
    }

    @Test
    void findById_ShouldThrowException_WhenProductDoesNotExist() {
        // Arrange
        Long id = 1L;
        when(productRepositoryPort.existsById(id)).thenReturn(false);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            productService.findById(id);
        });

        assertEquals("Product not found with id: 1", exception.getMessage());
        verify(productRepositoryPort, never()).findById(id);
    }

    @Test
    void findById_ShouldReturnProduct_WhenExists() {
        // Arrange
        int id = 1;
        Product product = Product.builder().build();
        product.setId(id);

        when(productRepositoryPort.existsById(id)).thenReturn(true);
        when(productRepositoryPort.findById((long) id)).thenReturn(Optional.of(product));

        // Act
        Optional<Product> result = productService.findById((long) id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(productRepositoryPort).existsById(id);
        verify(productRepositoryPort).findById((long) id);
    }
}
