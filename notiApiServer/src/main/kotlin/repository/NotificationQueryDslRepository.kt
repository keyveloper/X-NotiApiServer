package org.example.repository

import org.example.entity.Notification

interface NotificationQueryDslRepository {
    fun findInitAll(receiverId: Long): List<Notification>

    fun findPrevAll(receiverId: Long, startId: Long): List<Notification>

    fun findNextAll(receiverId: Long, endId: Long): List<Notification>

    fun saveAll(requests: List<Notification>): Long
}