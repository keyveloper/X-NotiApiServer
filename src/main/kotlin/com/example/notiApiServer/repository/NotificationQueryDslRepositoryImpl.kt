package com.example.notiApiServer.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.example.notiApiServer.entity.Notification
import com.example.notiApiServer.entity.QNotification

class NotificationQueryDslRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
): NotificationQueryDslRepository {
    private val notification = QNotification.notification

    override fun findInitAll(receiverId: Long): List<Notification> {
        return jpaQueryFactory
            .selectFrom(notification)
            .where(notification.receiverId.eq(receiverId)
            )
            .orderBy(notification.id.desc())
            .limit(10)
            .fetch()
    }

    override fun findPrevAll(receiverId: Long, endId: Long): List<Notification> {
        return jpaQueryFactory
            .selectFrom(notification)
            .where(notification.receiverId.eq(receiverId)
                .and(notification.id.gt(endId))
            )
            .orderBy(notification.id.desc())
            .limit(10)
            .fetch()
    }

    override fun findNextAll(receiverId: Long, startId: Long): List<Notification> {
        return jpaQueryFactory
            .selectFrom(notification)
            .where(notification.receiverId.eq(receiverId)
                .and(notification.id.lt(startId))
            )
            .orderBy(notification.id.desc())
            .limit(10)
            .fetch()
    }
}