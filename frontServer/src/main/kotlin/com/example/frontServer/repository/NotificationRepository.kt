package com.example.frontServer.repository

import com.example.frontServer.entity.Notification
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationRepository: JpaRepository<Notification, Long> {
    fun findAllByReceiverId(receiver: Long): List<Notification>
}