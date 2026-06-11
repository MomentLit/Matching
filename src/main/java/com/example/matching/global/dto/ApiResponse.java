package com.example.matching.global.dto;

public record ApiResponse<T>(
        String message,
        T data
) {
}
