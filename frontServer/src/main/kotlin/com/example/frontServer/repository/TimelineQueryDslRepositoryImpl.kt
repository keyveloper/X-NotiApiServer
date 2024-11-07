package com.example.frontServer.repository

import com.example.frontServer.entity.QTimeline
import com.example.frontServer.entity.Timeline
import com.querydsl.jpa.impl.JPAQueryFactory
import java.time.LocalDateTime

class TimelineQueryDslRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
): TimelineQueryDslRepository {
    private val qTimeline = QTimeline.timeline
    override fun findAllByReceiverIdWithInOneDay(receiverId: Long): List<Timeline> {
        return jpaQueryFactory
            .selectFrom(qTimeline)
            .where(
                qTimeline.receiverId.eq(receiverId)
                .and(qTimeline.createdAt.after(LocalDateTime.now().minusDays(1)))
            )
            .fetch()
    }
}