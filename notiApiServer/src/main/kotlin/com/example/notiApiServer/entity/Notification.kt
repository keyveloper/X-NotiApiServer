package com.example.notiApiServer.entity

import com.example.notiApiServer.enum.NotificationType
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "notifications")
@EntityListeners(AuditingEntityListener::class)
class Notification(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    val publisherId:Long,

    val receiverId:Long,

    val notificationType: NotificationType,

    val targetBoardId: Long?,

    @CreatedDate
    var createdAt: LocalDateTime?,
    )