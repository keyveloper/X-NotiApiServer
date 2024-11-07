package com.example.liveApiServer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LiveApiServerApplication

fun main(args: Array<String>) {
    runApplication<com.example.liveApiServer.LiveApiServerApplication>(*args)
}
