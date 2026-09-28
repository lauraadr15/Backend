package com.ridemove.infrastructure.adapter.in.web.dto;

public record LoginResponse(String token, String tipo) {
    public static LoginResponse deToken(String token) {
        return new LoginResponse(token, "Bearer");
    }
}
