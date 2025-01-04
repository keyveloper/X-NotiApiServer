package com.example.notiApiServer.controller

import com.example.notiApiServer.dto.NotificationServerSaveResponse
import com.example.notiApiServer.dto.NotificationGetRequest
import com.example.notiApiServer.dto.NotificationGetServerResponse
import com.example.notiApiServer.dto.NotificationSaveRequest
import com.example.notiApiServer.enum.MSAServerErrorCode
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
                errorCode = MSAServerErrorCode.SUCCESS,
                errorDetails = null
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
                errorCode = MSAServerErrorCode.SUCCESS,
                errorDetails = null
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
                errorCode = MSAServerErrorCode.SUCCESS,
                errorDetails = null
            )
        )
    }

    @PostMapping("/notification/save")
    fun saveAll(
        @RequestBody requests: List<NotificationSaveRequest>
    ): ResponseEntity<NotificationServerSaveResponse> {
        return ResponseEntity.ok().body(
            NotificationServerSaveResponse(
                savedRow = notificationService.saveAll(requests),
                errorCode = MSAServerErrorCode.SUCCESS,
                errorDetails = null
            )
        )
    }
}