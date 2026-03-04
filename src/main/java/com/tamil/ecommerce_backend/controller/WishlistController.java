package com.tamil.ecommerce_backend.controller;

import com.tamil.ecommerce_backend.service.WishlistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.tamil.ecommerce_backend.model.Product;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    //  Add to wishlist
    @PostMapping("/{productId}")
    public String addToWishlist(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String productId) {

        return wishlistService.addToWishlist(authHeader, productId);
    }

    //  Get wishlist
    @GetMapping
    public List<Product> getWishlist(
            @RequestHeader("Authorization") String authHeader) {

        return wishlistService.getWishlist(authHeader);
    }

    //  Remove from wishlist
    @DeleteMapping("/{productId}")
    public String removeFromWishlist(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String productId) {

        return wishlistService.removeFromWishlist(authHeader, productId);
    }
}