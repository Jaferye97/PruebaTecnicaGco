package com.yeimer.ecommerceapi.infra.adapters.in.controllers;

import com.yeimer.ecommerceapi.application.useCases.Product.*;
import com.yeimer.ecommerceapi.domain.pojos.Product;
import com.yeimer.ecommerceapi.infra.adapters.in.controllers.dto.productDto.ProductDTO;
import com.yeimer.ecommerceapi.infra.adapters.in.controllers.mapper.ProductMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    private final GetProductById getProductById;
    private final CreateProduct createProduct;
    private final GetProductAll getProductAll;
    private final UpdateProduct updateProduct;
    private final ToggleIsActiveProduct toggleIsActiveProduct;

    public ProductController(
            GetProductById getProductById,
            CreateProduct createProduct,
            GetProductAll getProductAll,
            UpdateProduct updateProduct,
            ToggleIsActiveProduct toggleIsActiveProduct) {
        this.getProductById = getProductById;
        this.createProduct = createProduct;
        this.getProductAll = getProductAll;
        this.updateProduct = updateProduct;
        this.toggleIsActiveProduct = toggleIsActiveProduct;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProductById(
            @PathVariable long id
    ){
        Optional<Product> product = getProductById.findById(id);
        if(product.isPresent())
            return ResponseEntity.ok(ProductMapper.toProductDto(product.orElse(null)));
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/product")
    public ResponseEntity<ProductDTO> createProduct(
            @RequestBody ProductDTO productDTO
    ){
        Product product = ProductMapper.toAppObject(productDTO);
        Product createdProduct = createProduct.save(product);
        return ResponseEntity.ok(ProductMapper.toProductDto(createdProduct));
    }

    @PostMapping("/product/update")
    public ResponseEntity<ProductDTO> updateProduct(
            @RequestBody ProductDTO productDTO
    ){
        Product product = ProductMapper.toAppObject(productDTO);
        Product updatedProduct = updateProduct.update(product);
        return ResponseEntity.ok(ProductMapper.toProductDto(updatedProduct));
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        List<Product> product = getProductAll.getAll();
        List<ProductDTO> productDTOS = product.stream().map(ProductMapper::toProductDto).toList();

        return ResponseEntity.ok(productDTOS);
    }

    @PostMapping("/product/toggleIsActive/{id}")
    public ResponseEntity<ProductDTO> toggleIsActive(
            @PathVariable long id
    ){
        Product updatedProduct = toggleIsActiveProduct.toggleIsActiveById(id);
        return ResponseEntity.ok(ProductMapper.toProductDto(updatedProduct));
    }
}
