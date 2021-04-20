package com.demo.wskafkaorder.config

import com.demo.wskafkaorder.events.model.Notification
import com.demo.wskafkaorder.events.model.OrderCreatedEvent
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class KafkaConfigTest{
    @Test
    fun test1(){
        println(Notification.TOPIC)
        println(OrderCreatedEvent.TOPIC)

    }
}