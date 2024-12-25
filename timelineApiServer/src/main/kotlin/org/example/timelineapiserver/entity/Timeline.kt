package org.example.timelineapiserver.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Entity
@Table(name = "timelines")
@EntityListeners(AuditingEntityListener::class)
class Timeline(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    val receiverId: Long,

    val boardId: Long,

    @CreatedDate
    var createdAt: LocalDateTime?
)