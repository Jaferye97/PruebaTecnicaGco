package com.yeimer.ecommerceapi.infra.adapters.in.controllers.mapper;

import com.yeimer.ecommerceapi.domain.pojos.Product;
import com.yeimer.ecommerceapi.domain.pojos.ProductWithMovement;
import com.yeimer.ecommerceapi.infra.adapters.in.controllers.dto.productDto.ProductDTO;
import com.yeimer.ecommerceapi.infra.adapters.in.controllers.dto.productDto.ProductWithMovementDTO;

import java.util.stream.Collectors;


public class ProductMapper {
    public static ProductDTO toProductDto(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .category(product.getCategory())
                .code(product.getCode())
                .dateCreation(product.getDateCreation())
                .isActive(product.isActive())
                .build();
    }

    public static Product toAppObject(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .stock(productDTO.getStock())
                .category(productDTO.getCategory())
                .code(productDTO.getCode())
                .dateCreation(productDTO.getDateCreation())
                .isActive(productDTO.isActive())
                .build();
    }

    public static ProductWithMovementDTO toProductWithMovementDto(ProductWithMovement product) {
        return ProductWithMovementDTO.builder()
                .product(toProductDto(product.getProduct()))
                .movements(product.getMovements().stream().map(MovementMapper::toMovementDto).collect(Collectors.toList()))
                .build();
    }
}
