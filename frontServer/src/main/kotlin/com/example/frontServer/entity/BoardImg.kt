package com.example.frontServer.entity

import jakarta.persistence.*

@Entity
class BoardImg(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "token")
    var token: String,

    var url: String, // file system url

    var filename: String,

    )