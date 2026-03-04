package com.tamil.ecommerce_backend.controller;

import com.tamil.ecommerce_backend.dto.ProductRequest;
import com.tamil.ecommerce_backend.model.Product;
import com.tamil.ecommerce_backend.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product addProduct(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody ProductRequest request) {

        return productService.addProduct(authHeader, request);
    }

    @PutMapping("/{id}")
    public Product updateProduct(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String id,
            @RequestBody ProductRequest request) {

        return productService.updateProduct(authHeader, id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String id) {

        return productService.deleteProduct(authHeader, id);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
}