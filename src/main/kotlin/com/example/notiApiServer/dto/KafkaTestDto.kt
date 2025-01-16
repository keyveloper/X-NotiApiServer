package com.example.notiApiServer.dto

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

data class KafkaTestDto(
    val id: Long,
    val message: String,
    val username: String?
)
// {"id": 1, "message": "hello?"}
// nullable 처리 ?
// {"id": 2, "message": "nullable?"} -- ㅇㅇ 잘되네 ㄱㄱ