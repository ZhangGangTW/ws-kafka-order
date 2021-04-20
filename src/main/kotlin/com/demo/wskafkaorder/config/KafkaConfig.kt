package com.demo.wskafkaorder.config

import com.demo.wskafkaorder.events.model.Notification
import com.demo.wskafkaorder.events.model.NotificationSerializer
import com.demo.wskafkaorder.events.model.OrderCanceledEvent
import com.demo.wskafkaorder.events.model.OrderCreatedEvent
import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.config.ConfigResource
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import javax.annotation.PostConstruct


@Configuration
class KafkaConfig {
    @Value("\${spring.kafka.bootstrap-servers}")
    private lateinit var bootstrapServers: String

    @Autowired
    private lateinit var kafkaAdmin: KafkaAdmin

    @Bean
    fun adminClient(): AdminClient {
        return AdminClient.create(kafkaAdmin.configurationProperties)
    }

    @Bean
    fun notificationTopic(): NewTopic {
        return TopicBuilder.name(Notification.TOPIC).build()
    }

    @Bean
    fun orderCreatedEventTopic(): NewTopic {
        return TopicBuilder.name(OrderCreatedEvent.TOPIC).build()
    }

    @Bean
    fun orderCanceledEventTopic(): NewTopic {
        return TopicBuilder.name(OrderCanceledEvent.TOPIC).build()
    }

    @Bean
    fun notificationProducerFactory(): ProducerFactory<String, Notification> {
        val props: MutableMap<String, Any> = mutableMapOf()
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = NotificationSerializer::class.java
//        props[ProducerConfig.ACKS_CONFIG] = "all"
        return DefaultKafkaProducerFactory(props)
    }

    @Bean
    fun notificationKafkaTemplate(
        notificationProducerFactory: ProducerFactory<String, Notification>
    ): KafkaTemplate<String, Notification> {
        val kafkaTemplate = KafkaTemplate(notificationProducerFactory)
        kafkaTemplate.defaultTopic = Notification.TOPIC
        return kafkaTemplate
    }

    @Bean
    fun defaultProducerFactory(): ProducerFactory<String, String> {
        val props: MutableMap<String, Any> = HashMap()
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.ACKS_CONFIG] = "all"
        return DefaultKafkaProducerFactory(props)
    }

    @Bean
    fun defaultKafkaTemplate(
        defaultProducerFactory: ProducerFactory<String, String>
    ): KafkaTemplate<String, String> {
        return KafkaTemplate(defaultProducerFactory)
    }

    @PostConstruct
    fun after() {
        val describeConfigs =
            adminClient().describeConfigs(listOf(ConfigResource(ConfigResource.Type.TOPIC, OrderCreatedEvent.TOPIC)))
        describeConfigs.all().get().forEach { (t, u) ->
            println(t)
            println(u)
        }
    }
}