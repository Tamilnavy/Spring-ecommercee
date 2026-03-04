package com.tamil.ecommerce_backend.service;

import com.tamil.ecommerce_backend.model.Product;
import com.tamil.ecommerce_backend.model.Wishlist;
import com.tamil.ecommerce_backend.repository.ProductRepository;
import com.tamil.ecommerce_backend.repository.WishlistRepository;
import com.tamil.ecommerce_backend.util.AuthUtil;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final AuthUtil authUtil;

    public WishlistService(WishlistRepository wishlistRepository,
                           ProductRepository productRepository,
                           AuthUtil authUtil) {

        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.authUtil = authUtil;
    }

    public String addToWishlist(String authHeader, String productId) {

        String userId = authUtil.getLoggedInUser(authHeader).getId();

        if (wishlistRepository.existsByUserIdAndProductId(userId, productId)) {
            return "Product already in wishlist";
        }

        Wishlist wishlist = new Wishlist();
        wishlist.setUserId(userId);
        wishlist.setProductId(productId);

        wishlistRepository.save(wishlist);

        return "Added to wishlist";
    }

    public List<Product> getWishlist(String authHeader) {

        String userId = authUtil.getLoggedInUser(authHeader).getId();

        List<Wishlist> list = wishlistRepository.findByUserId(userId);

        return list.stream()
                .map(w -> productRepository.findById(w.getProductId()).orElse(null))
                .filter(p -> p != null)
                .toList();
    }

    public String removeFromWishlist(String authHeader, String productId) {

        String userId = authUtil.getLoggedInUser(authHeader).getId();

        wishlistRepository.deleteByUserIdAndProductId(userId, productId);

        return "Removed from wishlist";
    }
}