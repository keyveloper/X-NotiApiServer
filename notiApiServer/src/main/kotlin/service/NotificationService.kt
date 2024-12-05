package org.example.service

import org.example.dto.NotiResult
import org.example.dto.NotificationRequest
import org.example.dto.NotificationSaveRequest
import org.example.repository.NotificationRepository
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository
) {
    fun findInitAll(receiverId: Long): List<NotiResult>{

    }

    fun findPrevAll(request: NotificationRequest): List<NotiResult> {

    }

    fun findNextAll(request: NotificationRequest): List<NotiResult> {

    }

    fun saveAll(requests: List<NotificationSaveRequest>): Long {

    }
}