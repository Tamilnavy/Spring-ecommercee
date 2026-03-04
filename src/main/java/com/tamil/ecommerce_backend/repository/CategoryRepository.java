package com.tamil.ecommerce_backend.repository;

import com.tamil.ecommerce_backend.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
}