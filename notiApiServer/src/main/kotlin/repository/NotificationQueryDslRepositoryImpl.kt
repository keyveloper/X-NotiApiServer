package org.example.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.example.dto.NotificationRequest
import org.example.entity.Notification

class NotificationQueryDslRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
): NotificationQueryDslRepository {
    private val notification = QNotification.notification

    override fun findInitAll(receiverId: Long): List<Notification> {
        return jpaQueryFactory
            .selectFrom(notification)
            .where(notification.receiverId.eq(receiverId)
                .and(notification.isSent.eq(false))
            )
            .orderBy(notification.id.desc())
            .limit(10)
            .fetch()
    }

    override fun findPrevAll(receiverId: Long, startId: Long): List<Notification> {
        return jpaQueryFactory
            .selectFrom(notification)
            .whwere(notification.receiverId.eq(receiverId)
                .and(notification.isSent.eq(false))
                .and(notification.id.lt(startId))
            )
            .orderBy(notification.id.desc())
            .limit(10)
            .fetch()
    }

    override fun findNextAll(receiverId: Long, endId: Long): List<Notification> {
        return jpaQueryFactory
            .selectFrom(notification)
            .where(notification.receiverId.eq(receiverId)
                .and(notification.boardId.gt(endId))
                .and(notification.isSent.eq(false))
            )
            .orderBy(notification.id.dexc())
            .limit(10)
            .fetch()
    }

    override fun saveAll(requests: List<Notification>): Long {
        TODO("Not yet implemented")
    }
}