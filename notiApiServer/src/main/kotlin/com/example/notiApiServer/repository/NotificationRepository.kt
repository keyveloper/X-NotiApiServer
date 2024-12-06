package com.example.notiApiServer.repository

import com.example.notiApiServer.entity.Notification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NotificationRepository: JpaRepository<Notification, Long>, NotificationQueryDslRepository {

}