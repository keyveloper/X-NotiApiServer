package com.example.notiApiServer.controller

import com.example.notiApiServer.dto.NotificationSaveResponse
import com.example.notiApiServer.dto.NotificationGetRequest
import com.example.notiApiServer.dto.NotificationGetServerResponse
import com.example.notiApiServer.dto.NotificationSaveRequest
import com.example.notiApiServer.enum.NotificationServerCode
import com.example.notiApiServer.service.NotificationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class NotificationController(
    private val notificationService: NotificationService
) {
    @PostMapping("/notification/init")
    fun findInitAll(
        @RequestBody receiverId: Long
    ): ResponseEntity<NotificationGetServerResponse> {
        return ResponseEntity.ok().body(
            NotificationGetServerResponse.of(
                notificationService.findInitAll(receiverId),
                null,
                NotificationServerCode.SUCCESS
            )
        )
    }

    @PostMapping("/notification/prev")
    fun findPrevAll(
        @RequestBody request: NotificationGetRequest
    ): ResponseEntity<NotificationGetServerResponse> {
        return ResponseEntity.ok().body(
            NotificationGetServerResponse.of(
                notificationService.findPrevAll(request),
                null,
                NotificationServerCode.SUCCESS
            )
        )
    }

    @PostMapping("/notification/next")
    fun findNextAll(
        @RequestBody request: NotificationGetRequest
    ): ResponseEntity<NotificationGetServerResponse> {
        return ResponseEntity.ok().body(
            NotificationGetServerResponse.of(
                notificationService.findNextAll(request),
                null,
                NotificationServerCode.SUCCESS
            )
        )
    }

    @PostMapping("/notification/save")
    fun saveAll(
        @RequestBody requests: List<NotificationSaveRequest>
    ): ResponseEntity<NotificationSaveResponse> {
        return ResponseEntity.ok().body(
            NotificationSaveResponse(
            null,
            NotificationServerCode.SUCCESS
            )
        )
    }
}