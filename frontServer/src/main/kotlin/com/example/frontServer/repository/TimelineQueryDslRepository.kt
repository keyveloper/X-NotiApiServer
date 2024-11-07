package com.example.frontServer.repository

import com.example.frontServer.entity.Timeline

interface TimelineQueryDslRepository {
    fun findAllByReceiverIdWithInOneDay(receiverId: Long): List<Timeline>
}