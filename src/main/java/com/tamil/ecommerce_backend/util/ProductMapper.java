package com.tamil.ecommerce_backend.util;

import com.tamil.ecommerce_backend.dto.ProductRequest;
import com.tamil.ecommerce_backend.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequest request) {

        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setBrand(request.getBrand());
        product.setCategory(request.getCategory());
        product.setImage(request.getImage());

        if (request.getPrice() != null)
            product.setPrice(request.getPrice());

        if (request.getStock() != null)
            product.setStock(request.getStock());

        return product;
    }

    public void update(Product product, ProductRequest request) {

        if (request.getName() != null)
            product.setName(request.getName());

        if (request.getDescription() != null)
            product.setDescription(request.getDescription());

        if (request.getBrand() != null)
            product.setBrand(request.getBrand());

        if (request.getCategory() != null)
            product.setCategory(request.getCategory());

        if (request.getImage() != null)
            product.setImage(request.getImage());

        if (request.getPrice() != null)
            product.setPrice(request.getPrice());

        if (request.getStock() != null)
            product.setStock(request.getStock());
    }
}