package com.ventas.apiventas.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        LocalDateTime timestamp,
        String title,
        int status,
        String detail,
        String instance,
        Map<String, String> errors
) {
    public ErrorResponse(String title, int status, String detail, String instance, Map<String, String> errors) {
        this(LocalDateTime.now(), title, status, detail, instance, errors);
    }

    public ErrorResponse(String title, int status, String detail, String instance) {
        this(LocalDateTime.now(), title, status, detail, instance, null);
    }
}