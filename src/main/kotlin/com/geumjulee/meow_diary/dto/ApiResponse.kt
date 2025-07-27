package com.geumjulee.meow_diary.dto

data class ApiResponse<T>(
    val success: Boolean,
    val message: String? = null,
    val data: T? = null,
    val error: String? = null
) {
    companion object {
        fun <T> success(data: T, message: String? = null): ApiResponse<T> {
            return ApiResponse(true, message, data, null)
        }

        fun <T> error(message: String, error: String? = null): ApiResponse<T> {
            return ApiResponse(false, message, null, error)
        }
    }
} 