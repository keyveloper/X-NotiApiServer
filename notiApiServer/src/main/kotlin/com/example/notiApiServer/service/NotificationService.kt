package com.example.notiApiServer.service

import com.example.notiApiServer.dto.NotificationGetServerResult
import com.example.notiApiServer.dto.NotificationGetRequest
import com.example.notiApiServer.dto.NotificationSaveRequest
import com.example.notiApiServer.entity.Notification
import com.example.notiApiServer.repository.NotificationRepository
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository
) {
    fun findInitAll(receiverId: Long): List<NotificationGetServerResult>{
        return notificationRepository.findInitAll(receiverId)
            .map { NotificationGetServerResult.of(it) }
    }

    fun findPrevAll(request: NotificationGetRequest): List<NotificationGetServerResult> {
        return notificationRepository.findPrevAll(request.receiverId, request.startId)
            .map { NotificationGetServerResult.of(it) }
    }

    fun findNextAll(request: NotificationGetRequest): List<NotificationGetServerResult> {
        return notificationRepository.findNextAll(request.receiverId, request.endId)
            .map { NotificationGetServerResult.of(it) }
    }

    fun saveAll(requests: List<NotificationSaveRequest>): Long {
        return notificationRepository.saveAll(
            requests.map {
                Notification(
                    publisherId = it.receiverId,
                    receiverId = it.receiverId,
                    message =  it.message,
                    id = null,
                    createdAt = null,
                    isSent = false
                )
            }
        )
    }
}