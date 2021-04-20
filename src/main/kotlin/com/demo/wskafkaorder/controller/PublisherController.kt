package com.demo.wskafkaorder.controller

import com.demo.wskafkaorder.controller.request.CreateOrderRequest
import com.demo.wskafkaorder.controller.request.NotificationRequest
import com.demo.wskafkaorder.controller.response.ApiResponse
import com.demo.wskafkaorder.events.model.Notification
import com.demo.wskafkaorder.events.model.OrderCreatedEvent
import com.demo.wskafkaorder.service.PublisherService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.*

@RestController
@RequestMapping("/events")
class PublisherController(
    private val publisherService: PublisherService
) {
    @PostMapping("/notifications")
    fun sendNotification(@RequestBody request: NotificationRequest): ApiResponse {
        return try {
            publisherService.sendNotification(
                Notification(
                    id = UUID.randomUUID().toString(),
                    from = request.from,
                    content = request.content,
                    createAt = Instant.now(),
                    urgent = request.urgent
                )
            )
            ApiResponse.ok()
        } catch (e: Exception) {
            ApiResponse.error(e)
        }
    }

    @PostMapping("/notifications2")
    fun sendNotification2(@RequestBody request: NotificationRequest): ApiResponse {
        return try {
            publisherService.sendNotification2(
                Notification(
                    id = UUID.randomUUID().toString(),
                    from = request.from,
                    content = request.content,
                    createAt = Instant.now(),
                    urgent = request.urgent
                )
            )
            ApiResponse.ok()
        } catch (e: Exception) {
            ApiResponse.error(e)
        }
    }

    @PostMapping("/orders")
    fun createOrder(@RequestBody request: CreateOrderRequest): ApiResponse {
        return try {
            publisherService.sendOrderCreatedEvent(
                OrderCreatedEvent(
                    id = UUID.randomUUID().toString(),
                    productionId = request.productionId,
                    price = request.price,
                    number = request.number
                )
            )
            ApiResponse.ok()
        } catch (e: Exception) {
            ApiResponse.error(e)
        }
    }
}