package org.example.controller

import dto.NotiServerResponse
import org.example.dto.NotiSaveResponse
import org.example.dto.NotificationRequest
import org.example.dto.NotificationSaveRequest
import org.example.service.NotificationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class NotificationController(
    private val notificationService: NotificationService
) {
    @GetMapping("/notification/init")
    fun findInitAll(
        @RequestBody receiverId: Long
    ): ResponseEntity<NotiServerResponse> {
        return ResponseEntit.
    }

    @GetMapping("/notification/prev")
    fun findPrevAll(
        @RequestBody request: NotificationRequest
    ): ResponseEntity<NotiServerResponse> {

    }

    @GetMapping("/notification/next")
    fun findNextAll(
        @RequestBody request: NotificationRequest
    ): ResponseEntity<NotiServerResponse> {

    }

    @PostMapping("/notification/save")
    fun saveAll(
        @RequestBody requests: List<NotificationSaveRequest>
    ): ResponseEntity<NotiSaveResponse> {

    }
}