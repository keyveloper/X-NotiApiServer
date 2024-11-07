package com.example.frontServer.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "timelines")
@EntityListeners(AuditingEntityListener::class)
class Timeline(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val receiverId: Long,

    val boardId: Long,

    @CreatedDate
    var createdAt: LocalDateTime? = null
)