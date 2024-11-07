package com.example.frontServer.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "boards")
@EntityListeners(AuditingEntityListener::class)
class Board (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val writerId : Long,

    var textContent: String,

    var fileApiUrl: String? = null, // api url

    @CreatedDate
    var createdAt: LocalDateTime? = null,

    @LastModifiedDate
    var lastModifiedAt: LocalDateTime? = null,

    var readingCount : Long = 0,

    var parentId: Long? = null,

    var invalid: Boolean = false,
    )