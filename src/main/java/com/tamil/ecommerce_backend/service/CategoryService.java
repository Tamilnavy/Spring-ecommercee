package com.tamil.ecommerce_backend.service;

import com.tamil.ecommerce_backend.dto.CategoryRequest;
import com.tamil.ecommerce_backend.model.Category;
import com.tamil.ecommerce_backend.repository.CategoryRepository;
import com.tamil.ecommerce_backend.util.AuthUtil;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final AuthUtil authUtil;

    public CategoryService(CategoryRepository categoryRepository,
                           AuthUtil authUtil) {

        this.categoryRepository = categoryRepository;
        this.authUtil = authUtil;
    }

    public Category createCategory(String authHeader, CategoryRequest request) {

        authUtil.validateAdmin(authHeader);

        Category category = new Category();
        category.setName(request.getName());

        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories(String authHeader) {

        authUtil.validateAdmin(authHeader);

        return categoryRepository.findAll();
    }

    public Category updateCategory(String authHeader,
                                   String id,
                                   CategoryRequest request) {

        authUtil.validateAdmin(authHeader);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found")
                );

        if (request.getName() != null)
            category.setName(request.getName());

        return categoryRepository.save(category);
    }

    public String deleteCategory(String authHeader, String id) {

        authUtil.validateAdmin(authHeader);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found")
                );

        categoryRepository.delete(category);

        return "Category deleted successfully";
    }
}