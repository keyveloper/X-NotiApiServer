package com.example.frontServer.repository

import com.example.frontServer.entity.QRole
import com.example.frontServer.entity.QUser
import com.example.frontServer.entity.QUserRole

import com.example.frontServer.entity.Role
import com.querydsl.jpa.impl.JPAQueryFactory

class UserRoleQueryDslRepositoryImpl (
    private val queryFactory: JPAQueryFactory
) : UserRoleQueryDslRepository{
    private val userRole = QUserRole.userRole
    private val user = QUser.user
    private val role = QRole.role

    override fun findRolesByUsername(username: String): List<Role> {
        return queryFactory
            .select(role)
            .from(user)
            .join(userRole).on(user.id.eq(userRole.user))
            .join(role).on(userRole.role.eq(role.id))
            .where(user.username.eq(username))
            .fetch()
    }
}