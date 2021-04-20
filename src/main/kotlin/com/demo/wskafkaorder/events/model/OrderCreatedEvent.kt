package com.demo.wskafkaorder.events.model

data class OrderCreatedEvent(
    val id: String,
    val productionId: String,
    val price: Double,
    val number: Int
) {
    companion object {
        val TOPIC = OrderCreatedEvent::class.simpleName!!
    }
}