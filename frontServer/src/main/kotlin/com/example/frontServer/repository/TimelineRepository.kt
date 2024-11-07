package com.example.frontServer.repository

import com.example.frontServer.entity.Timeline
import org.springframework.data.jpa.repository.JpaRepository

interface TimelineRepository: JpaRepository<Timeline, Long>, TimelineQueryDslRepository{
}