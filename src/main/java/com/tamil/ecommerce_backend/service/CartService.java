package com.tamil.ecommerce_backend.service;

import com.tamil.ecommerce_backend.model.Cart;
import com.tamil.ecommerce_backend.model.Product;
import com.tamil.ecommerce_backend.repository.CartRepository;
import com.tamil.ecommerce_backend.repository.ProductRepository;
import com.tamil.ecommerce_backend.util.AuthUtil;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final AuthUtil authUtil;

    public CartService(CartRepository cartRepository,
                       ProductRepository productRepository,
                       AuthUtil authUtil) {

        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.authUtil = authUtil;
    }

    public String addToCart(String authHeader, String productId, Integer quantity) {

        String userId = authUtil.getLoggedInUser(authHeader).getId();

        Cart existing = cartRepository.findByUserIdAndProductId(userId, productId);

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
            cartRepository.save(existing);
            return "Cart updated";
        }

        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setProductId(productId);
        cart.setQuantity(quantity);

        cartRepository.save(cart);

        return "Added to cart";
    }

    public Map<String, Object> getCart(String authHeader) {

        String userId = authUtil.getLoggedInUser(authHeader).getId();

        List<Cart> cartItems = cartRepository.findByUserId(userId);

        List<Map<String, Object>> items = new ArrayList<>();
        double totalAmount = 0;

        for (Cart cart : cartItems) {

            Product product = productRepository
                    .findById(cart.getProductId())
                    .orElse(null);

            if (product != null) {

                double subtotal = product.getPrice() * cart.getQuantity();
                totalAmount += subtotal;

                Map<String, Object> item = new HashMap<>();
                item.put("product", product);
                item.put("quantity", cart.getQuantity());
                item.put("subtotal", subtotal);

                items.add(item);
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("items", items);
        response.put("totalAmount", totalAmount);

        return response;
    }

    public String removeFromCart(String authHeader, String productId) {

        String userId = authUtil.getLoggedInUser(authHeader).getId();

        cartRepository.deleteByUserIdAndProductId(userId, productId);

        return "Removed from cart";
    }
}