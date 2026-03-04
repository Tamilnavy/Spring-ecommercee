package com.tamil.ecommerce_backend.service;

import com.tamil.ecommerce_backend.dto.ProductRequest;
import com.tamil.ecommerce_backend.model.Product;
import com.tamil.ecommerce_backend.repository.ProductRepository;
import com.tamil.ecommerce_backend.util.AuthUtil;
import com.tamil.ecommerce_backend.util.ProductMapper;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final AuthUtil authUtil;

    public ProductService(ProductRepository productRepository,
                          ProductMapper productMapper,
                          AuthUtil authUtil) {

        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.authUtil = authUtil;
    }

    public Product addProduct(String authHeader, ProductRequest request) {

        authUtil.validateAdmin(authHeader);

        Product product = productMapper.toEntity(request);

        return productRepository.save(product);
    }

    public Product updateProduct(String authHeader,
                                 String id,
                                 ProductRequest request) {

        authUtil.validateAdmin(authHeader);

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")
                );

        productMapper.update(product, request);

        return productRepository.save(product);
    }

    public String deleteProduct(String authHeader, String id) {

        authUtil.validateAdmin(authHeader);

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")
                );

        productRepository.delete(product);

        return "Product deleted successfully";
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}