package org.example.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime



@Entity
class Notification(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    val publisherId:Long?,

    val receiverId:Long,

    val isSent: Boolean,

    @CreatedDate
    val createdAt: LocalDateTime?,

    val message: String
)