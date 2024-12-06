package com.example.notiApiServer.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.querydsl.sql.MySQLTemplates
import com.querydsl.sql.dml.SQLInsertClause
import com.example.notiApiServer.entity.Notification
import com.example.notiApiServer.entity.QNotification
import javax.sql.DataSource

class NotificationQueryDslRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
    private val dataSource: DataSource,   // DB connection management - HikariCP
    private val templates: MySQLTemplates   // optimize SQL syntax for each database
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
            .where(notification.receiverId.eq(receiverId)
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
                .and(notification.id.gt(endId))
                .and(notification.isSent.eq(false))
            )
            .orderBy(notification.id.desc())
            .limit(10)
            .fetch()
    }

    override fun saveAll(notifications: List<Notification>): Long {
        val connection = dataSource.connection
        return try {
            val insertClause = SQLInsertClause(connection, templates, notification)

            notifications.forEach { noti ->
                insertClause
                    .set(notification.publisherId, noti.publisherId)
                    .set(notification.receiverId, noti.receiverId)
                    .set(notification.isSent, noti.isSent)
                    .set(notification.createdAt, noti.createdAt)
                    .set(notification.message, noti.message)
                    .addBatch() // 각 알림을 배치에 추가
            }

            insertClause.execute() // 배치 실행 후 삽입된 레코드 수 반환
        } finally {
            connection.close() // 연결 닫기
        }
    }
}