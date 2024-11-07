package com.example.frontServer.repository

import com.example.frontServer.entity.Like
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LikeRepository : JpaRepository<Like,Long>, LikeQueryDslRepository{

}