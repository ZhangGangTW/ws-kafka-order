package com.demo.wskafkaorder.service

import com.demo.wskafkaorder.events.model.Notification
import com.demo.wskafkaorder.events.model.OrderCreatedEvent
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.messaging.support.GenericMessage
import org.springframework.stereotype.Service

@Service
class PublisherService(
    private val notificationKafkaTemplate: KafkaTemplate<String, Notification>,
    private val defaultKafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper
) {
    fun sendNotification(notification: Notification) {
        notificationKafkaTemplate.send(Notification.TOPIC, notification.id, notification)
    }

    fun sendNotification2(notification: Notification) {
        defaultKafkaTemplate.send(GenericMessage(notification))
    }

    fun sendOrderCreatedEvent(orderCreatedEvent: OrderCreatedEvent) {
        defaultKafkaTemplate.send(
            OrderCreatedEvent.TOPIC,
            orderCreatedEvent.id,
            objectMapper.writeValueAsString(orderCreatedEvent)
        )
    }
}