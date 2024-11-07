package org.example.websocketserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WebSocketServerApplication

fun main(args: Array<String>) {
	runApplication<WebSocketServerApplication>(*args)
}
