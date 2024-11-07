package com.example.frontServer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class FrontServerApplication

fun main(args: Array<String>) {
    runApplication<FrontServerApplication>(*args)
}
