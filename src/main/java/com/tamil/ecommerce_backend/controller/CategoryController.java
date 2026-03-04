package com.tamil.ecommerce_backend.controller;

import com.tamil.ecommerce_backend.dto.CategoryRequest;
import com.tamil.ecommerce_backend.model.Category;
import com.tamil.ecommerce_backend.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public Category createCategory(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody CategoryRequest request) {

        return categoryService.createCategory(authHeader, request);
    }

    @GetMapping
    public List<Category> getAllCategories(
            @RequestHeader("Authorization") String authHeader) {

        return categoryService.getAllCategories(authHeader);
    }

    @PutMapping("/{id}")
    public Category updateCategory(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String id,
            @RequestBody CategoryRequest request) {

        return categoryService.updateCategory(authHeader, id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String id) {

        return categoryService.deleteCategory(authHeader, id);
    }
}