package com.yeimer.ecommerceapi.infra.adapters.out.db.mapper;

import com.yeimer.ecommerceapi.domain.pojos.Product;
import com.yeimer.ecommerceapi.infra.adapters.out.db.entities.ProductEntity;

public class ProductEntityMapper {
    public static Product toDomain(ProductEntity entity) {
        return Product.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .category(entity.getCategory())
                .code(entity.getCode())
                .dateCreation(entity.getDateCreation())
                .build();
    }

    public static ProductEntity toEntity(Product domain) {
        ProductEntity entity = new ProductEntity();
        if(domain.getId() > 0)
            entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setPrice(domain.getPrice());
        entity.setStock(domain.getStock());
        entity.setCategory(domain.getCategory());
        entity.setCode(domain.getCode());
        entity.setDateCreation(domain.getDateCreation());
        return entity;
    }
}
