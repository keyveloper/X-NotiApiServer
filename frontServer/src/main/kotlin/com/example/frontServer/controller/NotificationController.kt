package com.example.frontServer.controller

import com.example.frontServer.dto.NotificationDto
import com.example.frontServer.service.NotificationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class NotificationController(
    private val notificationService: NotificationService
) {
    @GetMapping("/notification")
    fun findAllByReceiver(
        @RequestParam receiver: Long
    ): ResponseEntity<List<NotificationDto>> {
        return ResponseEntity.ok().body(
            notificationService.findAllByReceiver(receiver)
        )
    }
}