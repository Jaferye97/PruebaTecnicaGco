package com.yeimer.ecommerceapi.infra.adapters.in.controllers;

import com.yeimer.ecommerceapi.application.useCases.Product.CreateProduct;
import com.yeimer.ecommerceapi.application.useCases.Product.GetProductAll;
import com.yeimer.ecommerceapi.application.useCases.Product.GetProductById;
import com.yeimer.ecommerceapi.application.useCases.Product.UpdateProduct;
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

    public ProductController(GetProductById getProductById, CreateProduct createProduct, GetProductAll getProductAll, UpdateProduct updateProduct) {
        this.getProductById = getProductById;
        this.createProduct = createProduct;
        this.getProductAll = getProductAll;
        this.updateProduct = updateProduct;
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
}
