package com.demo.wskafkaorder.controller.request

data class NotificationRequest(
    val from: String,
    val content: String,
    val urgent: Boolean
)