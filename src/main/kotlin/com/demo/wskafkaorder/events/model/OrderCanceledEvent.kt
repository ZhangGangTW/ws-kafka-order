package com.demo.wskafkaorder.events.model

data class OrderCanceledEvent(
    val id: String
) {
    companion object {
        val TOPIC = OrderCanceledEvent::class.simpleName!!
    }
}