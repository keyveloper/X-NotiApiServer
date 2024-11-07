package com.example.frontServer.entity

import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "roles")
class Role (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String,
)