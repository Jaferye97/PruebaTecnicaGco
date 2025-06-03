package com.yeimer.ecommerceapi.infra.adapters.in.controllers;

import com.yeimer.ecommerceapi.application.useCases.Product.*;
import com.yeimer.ecommerceapi.domain.pojos.Product;
import com.yeimer.ecommerceapi.infra.adapters.in.controllers.dto.ErrorResponse;
import com.yeimer.ecommerceapi.infra.adapters.in.controllers.dto.productDto.ProductDTO;
import com.yeimer.ecommerceapi.infra.adapters.in.controllers.dto.productDto.ProductWithMovementDTO;
import com.yeimer.ecommerceapi.infra.adapters.in.controllers.mapper.ProductMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Product", description = "The User API. Contains all the operations that can be performed on a product")
public class ProductController {

    private final GetProductById getProductById;
    private final CreateProduct createProduct;
    private final GetProductAll getProductAll;
    private final UpdateProduct updateProduct;
    private final ToggleIsActiveProduct toggleIsActiveProduct;
    private final FindByCategoryContaining findByCategoryContaining;
    private final FindByCodeContaining findByCodeContaining;
    private final FindByNameContaining findByNameContaining;
    private final FindByIdWithMovements findByIdWithMovements;
    private final FindByCodeContainingAndActiveIsTrue findByCodeContainingAndActiveIsTrue;

    public ProductController(
            GetProductById getProductById,
            CreateProduct createProduct,
            GetProductAll getProductAll,
            UpdateProduct updateProduct,
            ToggleIsActiveProduct toggleIsActiveProduct,
            FindByCategoryContaining findByCategoryContaining,
            FindByCodeContaining findByCodeContaining,
            FindByNameContaining findByNameContaining, FindByIdWithMovements findByIdWithMovements, FindByCodeContainingAndActiveIsTrue findByCodeContainingAndActiveIsTrue) {
        this.getProductById = getProductById;
        this.createProduct = createProduct;
        this.getProductAll = getProductAll;
        this.updateProduct = updateProduct;
        this.toggleIsActiveProduct = toggleIsActiveProduct;
        this.findByCategoryContaining = findByCategoryContaining;
        this.findByCodeContaining = findByCodeContaining;
        this.findByNameContaining = findByNameContaining;
        this.findByIdWithMovements = findByIdWithMovements;
        this.findByCodeContainingAndActiveIsTrue = findByCodeContainingAndActiveIsTrue;
    }

    @Operation(
            summary = "Get product by ID",
            description = "Returns a product by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Product found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Product not found or internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    )
    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProductById(
            @PathVariable long id
    ){
        Optional<Product> product = getProductById.findById(id);

        return ResponseEntity.ok(ProductMapper.toProductDto(product.orElse(null)));
    }

    @Operation(
            summary = "Create a new product",
            description = "Creates a new product. The 'code' must be unique."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Product created successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Validation failed",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Product with same code already exists or internal error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    )
    @PostMapping("/product")
    public ResponseEntity<ProductDTO> createProduct(
            @RequestBody ProductDTO productDTO
    ){
        Product product = ProductMapper.toAppObject(productDTO);
        Product createdProduct = createProduct.save(product);
        return ResponseEntity.ok(ProductMapper.toProductDto(createdProduct));
    }

    @Operation(
            summary = "Update an existing product",
            description = "Updates an existing product. The product must exist, and the code must be unique."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Product updated successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Validation error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Product not found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Product with same code already exists or internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    )
    @PutMapping("/product/update")
    public ResponseEntity<ProductDTO> updateProduct(
            @RequestBody ProductDTO productDTO
    ){
        Product product = ProductMapper.toAppObject(productDTO);
        Product updatedProduct = updateProduct.update(product);
        return ResponseEntity.ok(ProductMapper.toProductDto(updatedProduct));
    }

    @Operation(
            summary = "Get all products",
            description = "Returns a list of all products available"
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of products retrieved successfully",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class))
            )
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    )
    @GetMapping("/product")
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        List<Product> product = getProductAll.getAll();
        List<ProductDTO> productDTOS = product.stream().map(ProductMapper::toProductDto).toList();

        return ResponseEntity.ok(productDTOS);
    }

    @Operation(
            summary = "Toggle product active status",
            description = "Switches the 'isActive' status of a product by its ID. If active, it becomes inactive and vice versa."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Product status updated successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Product not found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    )
    @PostMapping("/product/toggleIsActive/{id}")
    public ResponseEntity<ProductDTO> toggleIsActive(
            @PathVariable long id
    ){
        Product updatedProduct = toggleIsActiveProduct.toggleIsActiveById(id);
        return ResponseEntity.ok(ProductMapper.toProductDto(updatedProduct));
    }

    @Operation(
            summary = "Find products by category",
            description = "Returns a list of products whose category contains the given text (case-insensitive)."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Products found",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class))
            )
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    )
    @GetMapping("/product/findByCategory/{category}")
    public ResponseEntity<List<ProductDTO>> findByCategoryContaining(
            @PathVariable String category
    ){
        List<Product> product = findByCategoryContaining.findByCategoryContaining(category);
        List<ProductDTO> productDTOS = product.stream().map(ProductMapper::toProductDto).toList();

        return ResponseEntity.ok(productDTOS);
    }

    @Operation(
            summary = "Find products by name",
            description = "Returns a list of products whose name contains the given text (case-insensitive)."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Products found successfully",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class))
            )
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    )
    @GetMapping("/product/findByName/{name}")
    public ResponseEntity<List<ProductDTO>> findByNameContaining(
            @PathVariable String name
    ){
        List<Product> product = findByNameContaining.findByNameContaining(name);
        List<ProductDTO> productDTOS = product.stream().map(ProductMapper::toProductDto).toList();

        return ResponseEntity.ok(productDTOS);
    }

    @Operation(
            summary = "Find products by code",
            description = "Returns a list of products whose code contains the given text (case-insensitive)."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Products found successfully",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class))
            )
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    )
    @GetMapping("/product/findByCode/{code}")
    public ResponseEntity<List<ProductDTO>> findByCodeContaining(
            @PathVariable String code
    ){
        List<Product> product = findByCodeContaining.findByCodeContaining(code);
        List<ProductDTO> productDTOS = product.stream().map(ProductMapper::toProductDto).toList();

        return ResponseEntity.ok(productDTOS);
    }

    @Operation(
            summary = "Find products by code and state is active",
            description = "Returns a list of products whose code contains the given text (case-insensitive)."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Products found successfully",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class))
            )
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    )
    @GetMapping("/product/findByCodeAndIsActive/{code}")
    public ResponseEntity<List<ProductDTO>> findByCodeContainingAndActiveIsTrue(
            @PathVariable String code
    ){
        List<Product> product = findByCodeContainingAndActiveIsTrue.findByCodeContainingAndActiveIsTrue(code);
        List<ProductDTO> productDTOS = product.stream().map(ProductMapper::toProductDto).toList();

        return ResponseEntity.ok(productDTOS);
    }

    @Operation(
            summary = "Get product with movements",
            description = "Returns a product along with its movement history (entries and exits)."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Product with movements retrieved successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProductWithMovementDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Product not found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    )
    @GetMapping("/products/{id}/movements")
    public ResponseEntity<ProductWithMovementDTO> getProductWithMovements(@PathVariable long id) {
        return ResponseEntity.ok(ProductMapper.toProductWithMovementDto(findByIdWithMovements.findByIdWithMovements(id)));
    }
}
