package com.example.frontServer.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy

@Entity
@Table(
    name = "followes",
    uniqueConstraints = [UniqueConstraint(columnNames = ["star", "fan"])]

)
class Follow(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "following_id")
    var followingId: Long,

    @CreatedBy
    @Column(name = "follower_id")
    var followerId: Long? = null
)