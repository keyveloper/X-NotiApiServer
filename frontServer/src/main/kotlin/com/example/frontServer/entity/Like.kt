package com.example.frontServer.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy

@Entity
@Table(
    name = "likes",
    uniqueConstraints = [UniqueConstraint(columnNames = ["boardId", "userId"])]
)
class Like(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "board_id")
    val boardId: Long,

    @CreatedBy
    @Column(name = "user_id")
    var userId: Long? = null,
)