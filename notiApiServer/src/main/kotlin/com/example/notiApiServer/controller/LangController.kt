package com.example.notiApiServer.controller

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
    @GetMapping("/like/lang")
    fun getLang(
        @RequestHeader(value="Accept-Language", defaultValue = "en") language: String
    ): ResponseEntity<String> {
        val locale = Locale.forLanguageTag(language)
        return ResponseEntity.ok().body(
            messageSource.getMessage("noti.like", null, locale)
        )
    }
}