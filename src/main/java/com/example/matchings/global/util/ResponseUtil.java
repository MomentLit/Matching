package com.example.matchings.global.util;

import com.example.matchings.global.dto.ApiResponse;

public class ResponseUtil {

    private ResponseUtil() {
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(message, data);
    }
}
