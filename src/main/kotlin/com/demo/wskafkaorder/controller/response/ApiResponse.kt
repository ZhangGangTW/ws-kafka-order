package com.demo.wskafkaorder.controller.response

data class ApiResponse(
    val success: Boolean,
    val message: String? = null
) {
    companion object {
        fun ok(): ApiResponse {
            return ApiResponse(true)
        }

        fun error(e: Exception? = null): ApiResponse {
            return ApiResponse(false, e?.message)
        }
    }
}
