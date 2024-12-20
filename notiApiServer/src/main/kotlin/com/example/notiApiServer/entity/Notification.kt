package com.example.notiApiServer.entity

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

    val isSent: Boolean,

    @CreatedDate
    var createdAt: LocalDateTime?,

    val message: String
)