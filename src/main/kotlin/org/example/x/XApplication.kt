package org.example.x

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class XApplication

fun main(args: Array<String>) {
    runApplication<XApplication>(*args)
}
