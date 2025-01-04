package com.example.frontServer.controller.api

import com.example.frontServer.dto.notification.request.NotificationGetRequest
import com.example.frontServer.dto.notification.response.NotificationGetResponse
import com.example.frontServer.service.noti.NotificationApiService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class NotificationController(
    private val notificationService: NotificationApiService
) {
    @PostMapping("/notification/init")
    fun findInitAll(
        @RequestBody receiverId: Long,
        @RequestHeader(value = "Accept-Language", defaultValue = "en") language: String
    ): ResponseEntity<NotificationGetResponse> {
        return ResponseEntity.ok().body(
            NotificationGetResponse.of(
                notificationService.fetchInitAll(receiverId, language),
                FrontServerCode.SUCCESS
            )
        )
    }

    @PostMapping("/notification/prev")
    fun findPrevAll(
        @RequestBody request: NotificationGetRequest,
        @RequestHeader(value = "Accept-Language", defaultValue = "en") language: String
    ): ResponseEntity<NotificationGetResponse> {
        return ResponseEntity.ok().body(
            NotificationGetResponse.of(
                notificationService.fetchPrevAll(request, language),
                FrontServerCode.SUCCESS
            )
        )
    }

    @PostMapping("/notification/next")
    fun findNextAll(
        @RequestBody request: NotificationGetRequest,
        @RequestHeader(value = "Accept-Language", defaultValue = "en") language: String
    ): ResponseEntity<NotificationGetResponse> {
        return ResponseEntity.ok().body(
            NotificationGetResponse.of(
                notificationService.fetchNextAll(request, language),
                FrontServerCode.SUCCESS
            )
        )
    }
}