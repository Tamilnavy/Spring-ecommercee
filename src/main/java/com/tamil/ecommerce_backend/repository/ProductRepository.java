package com.tamil.ecommerce_backend.repository;

import com.tamil.ecommerce_backend.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}

