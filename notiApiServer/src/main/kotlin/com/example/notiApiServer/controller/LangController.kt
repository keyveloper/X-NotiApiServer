package com.example.notiApiServer.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.MessageSource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class LangController(
    private val messageSource: MessageSource
) {
    private val logger = KotlinLogging.logger {}
    @GetMapping("/noti/lang")
    fun getLang(
        @RequestHeader(value="Accept-Language", defaultValue = "en") language: String
    ): ResponseEntity<String> {
        logger.info {"language ${language}"}
        val locale = Locale.forLanguageTag(language)
        return ResponseEntity.ok().body(
            messageSource.getMessage("noti.like", null, locale)
        )
    }
}