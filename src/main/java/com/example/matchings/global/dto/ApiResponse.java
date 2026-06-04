package com.example.matchings.global.dto;

public record ApiResponse<T>(
        String message,
        T data
) {
}
