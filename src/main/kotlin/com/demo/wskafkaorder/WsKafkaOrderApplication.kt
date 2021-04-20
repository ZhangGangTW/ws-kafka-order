package com.demo.wskafkaorder

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@EnableWebFlux
@SpringBootApplication
class WsKafkaOrderApplication

fun main(args: Array<String>) {
    runApplication<WsKafkaOrderApplication>(*args)
}
