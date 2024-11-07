package com.example.frontServer.repository
import com.querydsl.core.types.dsl.Expressions

import com.example.frontServer.dto.BoardInfo
import com.example.frontServer.dto.BoardWithUsernameAndComment
import com.example.frontServer.entity.QBoard
import com.example.frontServer.entity.QUser
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class BoardQueryDslRepositoryImpl(
    val queryFactory: JPAQueryFactory
): BoardQueryDslRepository {
    private val board = QBoard.board
    private val user = QUser.user
    val childBoard = QBoard("childBoard") // 같은 테이블에 대해 두 번째 별칭 생성

    override fun findAllBoardInfo(): List<BoardInfo> {
        return queryFactory
            .select(
                board,
                user.username,
                childBoard,
            )
            .from(board)
            .join(user).on(board.writerId.eq(user.id))
            .leftJoin(childBoard).on(childBoard.parentId.eq(board.id))
            .fetch()
            .map { tuple ->
                BoardInfo(
                    board = tuple.get(board)!!,
                    username = tuple.get(user.username)!!,
                    commentCount = tuple.get(childBoard.count())!!
                )
            }
    }

    // replies 같이
    override fun findByBoardInfoWithComments(id: Long): BoardWithUsernameAndComment? {
        val childUser = QUser("childUser")
        val grandSonBoard = QBoard("grandSonBoard") // 댓글 수를 계산하기 위한 서브 별칭

        val results = queryFactory
            .select(
                board,
                user.username,
                childBoard,
                childUser.username,
                grandSonBoard.id.count()
            )
            .from(board)
            .join(user).on(board.writerId.eq(user.id))
            .leftJoin(childBoard).on(childBoard.parentId.eq(board.id))
            .join(user).on(childBoard.writerId.eq(user.id))
            .leftJoin(grandSonBoard).on(grandSonBoard.parentId.eq(childBoard.id))
            .where(board.id.eq(id))
            .groupBy(board.id, childBoard.id, user.username, childUser.username)
            .fetch()

        val mainBoardWithUsername = results.firstOrNull()?.let { result ->
            val mainBoard = result.get(board)
            val mainUsername = result.get(user.username)

            val comments = results.mapNotNull { row ->
                row.get(childBoard)?.let {child ->
                    BoardInfo(
                        board = child,
                        username = row.get(user.username)!!,
                        commentCount = row.get(grandSonBoard.id.count()) ?: 0L
                    )
                }
            }

            BoardWithUsernameAndComment(
                boardWithUsername = BoardInfo(
                    board = mainBoard!!,
                    username = mainUsername!!,
                    commentCount = comments.size.toLong()
                ),
                comments = comments
            )
        }
        return mainBoardWithUsername
    }
}