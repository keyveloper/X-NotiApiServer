package com.example.frontServer.repository

import com.example.frontServer.entity.BoardImg
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardImgRepository: JpaRepository<BoardImg, Long> {
    fun findAllByToken(token: String): List<BoardImg>
}