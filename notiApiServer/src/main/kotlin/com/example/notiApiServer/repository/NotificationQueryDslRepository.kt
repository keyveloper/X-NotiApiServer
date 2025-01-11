package com.example.notiApiServer.repository

import com.example.notiApiServer.entity.Notification

interface NotificationQueryDslRepository {
    fun findInitAll(receiverId: Long): List<Notification>

    fun findPrevAll(receiverId: Long, endId: Long): List<Notification>

    fun findNextAll(receiverId: Long, startId: Long): List<Notification>
}