package com.example.liveApiServer.repository

import com.example.liveApiServer.entity.Like
import com.example.liveApiServer.entity.QLike
import com.example.liveApiServer.enum.LikeType
import com.querydsl.jpa.impl.JPAQueryFactory

class LikeQueryDslRepositoryImpl(
    private val queryFactory: JPAQueryFactory
):LikeQueryDslRepository {
    private val like = QLike.like
    override fun deleteLogically(boardId: Long) {
        queryFactory
            .update(like)
            .set(like.likeType, LikeType.NONE.ordinal)
            .where(like.boardId.eq(boardId))
            .execute()
    }
}