package org.example.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreateAt
import java.time.LocalDateTime


@Entity
class Notification(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    val publisherId:Long?,

    val receiverId:Long,

    val isSent: Boolean,

    @CreateAt
    val createdAt: LocalDateTime?,
)