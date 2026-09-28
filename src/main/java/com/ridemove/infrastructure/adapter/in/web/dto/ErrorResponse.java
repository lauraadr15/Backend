package com.ridemove.infrastructure.adapter.in.web.dto;

import java.time.LocalDateTime;

public record ErrorResponse(int status, String mensaje, LocalDateTime fecha) {
    public static ErrorResponse de(int status, String mensaje) {
        return new ErrorResponse(status, mensaje, LocalDateTime.now());
    }
}
