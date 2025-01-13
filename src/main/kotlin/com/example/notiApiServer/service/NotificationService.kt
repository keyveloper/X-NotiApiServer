package com.example.notiApiServer.service

import com.example.notiApiServer.dto.NotificationGetServerResult
import com.example.notiApiServer.dto.NotificationGetRequest
import com.example.notiApiServer.dto.NotificationSaveRequest
import com.example.notiApiServer.entity.Notification
import com.example.notiApiServer.repository.NotificationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository
) {
    fun findInitAll(receiverId: Long): List<NotificationGetServerResult>{
        return notificationRepository.findInitAll(receiverId)
            .map { NotificationGetServerResult.of(it) }
    }

    fun findPrevAll(request: NotificationGetRequest): List<NotificationGetServerResult> {
        return notificationRepository.findPrevAll(request.receiverId, request.endId)
            .map { NotificationGetServerResult.of(it) }
    }

    fun findNextAll(request: NotificationGetRequest): List<NotificationGetServerResult> {
        return notificationRepository.findNextAll(request.receiverId, request.startId)
            .map { NotificationGetServerResult.of(it) }
    }

    @Transactional
    fun saveAll(requests: List<NotificationSaveRequest>): Int {
        // save all language
        return notificationRepository.saveAll(
            requests.map {
                Notification(
                    id = null,
                    publisherId = it.publisherId,
                    receiverId = it.receiverId,
                    notificationType = it.notificationType,
                    targetBoardId = it.targetBoardId,
                    createdAt = null,
                )
            }
        ).size
    }
}