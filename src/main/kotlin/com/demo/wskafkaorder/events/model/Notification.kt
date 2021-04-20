package com.demo.wskafkaorder.events.model

import org.apache.kafka.common.serialization.Serializer
import org.springframework.util.SerializationUtils
import java.io.Serializable
import java.time.Instant

data class Notification(
    val id: String,
    val from: String,
    val content: String,
    val createAt: Instant,
    val urgent: Boolean,
) : Serializable {
    companion object {
        val TOPIC = Notification::class.simpleName!!
    }
}


class NotificationSerializer : Serializer<Notification> {
    override fun serialize(topic: String, data: Notification): ByteArray? {
        return SerializationUtils.serialize(data)
    }
}