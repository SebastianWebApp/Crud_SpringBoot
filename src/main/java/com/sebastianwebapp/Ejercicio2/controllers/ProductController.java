package com.sebastianwebapp.Ejercicio2.controllers;

import com.sebastianwebapp.Ejercicio2.domain.Product;
import com.sebastianwebapp.Ejercicio2.domain.ProductDTO;
import com.sebastianwebapp.Ejercicio2.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "Endpoints for managing products")

public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }


    @Operation(summary = "Get all products (without ID)", description = "Returns all products excluding the ID field")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No products found")
    })

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        var list = service.getAllWithoutId();
        if (list.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Get all products (with ID)", description = "Returns all products including their IDs")

    @GetMapping("/with-id")
    public ResponseEntity<List<Product>> getAllProductsWithId(){
        var list = service.getAll();
        if(list.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Filter products", description = "Filters products by name and/or category")

    @GetMapping("/filter")
    public ResponseEntity<List<ProductDTO>> filterProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category
    ){
        var filtered = service.filter(name, category);
        if(filtered.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(filtered);
    }


    @Operation(summary = "Add a new product", description = "Creates a new product with validation")

    @PostMapping
    public ResponseEntity<String> addProduct(@Valid @RequestBody Product product){
        service.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Product added " + product.name());
    }

    @Operation(summary = "Update an existing product", description = "Updates a product based on its ID")

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id ,@Valid @RequestBody ProductDTO productDTO){
        boolean updated = service.updateProduct(id, productDTO);
        if(updated){
            return ResponseEntity.ok("Product updated successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
    }

    @Operation(summary = "Delete a product", description = "Deletes a product by its ID")

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        boolean delete = service.deleteProduct(id);

        if(delete){
            return ResponseEntity.ok("Product delete successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
    }


}
