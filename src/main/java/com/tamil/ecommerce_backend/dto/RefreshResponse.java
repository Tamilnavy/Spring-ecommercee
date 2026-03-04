package com.tamil.ecommerce_backend.dto;

public class RefreshResponse {

    private String token;

    public RefreshResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}