package com.example.frontServer.repository

import com.example.frontServer.entity.QLike
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class LikeQueryDslRepositoryImpl(
    val queryFactory: JPAQueryFactory
): LikeQueryDslRepository {
    private val qLike = QLike.like
    override fun findUsersByBoardId(boardId: Long): List<Long> {
        return queryFactory
            .select(qLike.userId)
            .from(qLike)
            .where(qLike.boardId.eq(boardId))
            .fetch()
    }
}