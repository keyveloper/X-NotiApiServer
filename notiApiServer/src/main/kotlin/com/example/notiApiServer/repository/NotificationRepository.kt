package com.example.notiApiServer.repository

import com.example.notiApiServer.entity.Notification
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationRepository: JpaRepository<Notification, Long>, NotificationQueryDslRepository {
}