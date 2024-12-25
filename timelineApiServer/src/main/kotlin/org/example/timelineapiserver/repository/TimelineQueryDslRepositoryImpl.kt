package org.example.timelineapiserver.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.example.timelineapiserver.dto.request.TimelineGetRequest
import org.example.timelineapiserver.entity.Timeline

class TimelineQueryDslRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
): TimelineQueryDslRepository {
    private val timeline = QTimeline.timeline

    override fun findAllInit(timelineRequest: TimelineGetRequest): List<Timeline> {
        return jpaQueryFactory
            .selectFrom(timeline)
            .where(timeline.receiverId.eq(timelineRequest.receiverId))
            .orderBy(timeline.createdAt.desc())
            .limit(15)
            .fetch()
    }

    override fun findAllNext(timelineRequest: TimelineGetRequest): List<Timeline> {
        return jpaQueryFactory
            .selectFrom(timeline)
            .where(timeline.receiverId.eq(timelineRequest.receiverId)
                .and(timeline.id.gt(timelineRequest.endBoardId))
            )
            .orderBy(timeline.createdAt.desc())
            .limit(15)
            .fetch()
    }

    override fun findAllPrev(timelineRequest: TimelineGetRequest): List<Timeline> {
        return jpaQueryFactory
            .selectFrom(timeline)
            .where(timeline.receiverId.eq(timelineRequest.receiverId)
                .and(timeline.id.lt(timelineRequest.startBoardId))
            )
            .orderBy(timeline.createdAt.asc())
            .limit(15)
            .fetch()
    }
}