package com.tamil.ecommerce_backend.controller;

import com.tamil.ecommerce_backend.dto.CartRequest;
import com.tamil.ecommerce_backend.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public String addToCart(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody CartRequest request) {

        return cartService.addToCart(authHeader,
                request.getProductId(),
                request.getQuantity());
    }

    @GetMapping
    public Map<String, Object> getCart(
            @RequestHeader("Authorization") String authHeader) {

        return cartService.getCart(authHeader);
    }

    @DeleteMapping("/{productId}")
    public String removeFromCart(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String productId) {

        return cartService.removeFromCart(authHeader, productId);
    }
}