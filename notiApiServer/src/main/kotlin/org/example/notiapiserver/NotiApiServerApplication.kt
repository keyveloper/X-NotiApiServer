package org.example.notiapiserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NotiApiServerApplication

fun main(args: Array<String>) {
    runApplication<NotiApiServerApplication>(*args)
}
