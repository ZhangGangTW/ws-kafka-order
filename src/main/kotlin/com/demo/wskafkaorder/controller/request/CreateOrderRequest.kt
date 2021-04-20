package com.demo.wskafkaorder.controller.request

data class CreateOrderRequest(
    val productionId: String,
    val price: Double,
    val number: Int
)