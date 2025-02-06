package com.example.notiApiServer.dto.error

import java.time.LocalDateTime

data class KafkaDlqRecord(
    val topic: String,
    val value: String,
    val offset: Long,
    var revive: Boolean,
    var reviveDate: LocalDateTime?
)