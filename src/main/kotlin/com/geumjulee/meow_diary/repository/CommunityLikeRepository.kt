package com.geumjulee.meow_diary.repository

import com.geumjulee.meow_diary.entity.CommunityLike
import com.geumjulee.meow_diary.entity.CommunityPost
import com.geumjulee.meow_diary.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommunityLikeRepository : JpaRepository<CommunityLike, Long> {
    
    fun findByPostAndUser(post: CommunityPost, user: User): CommunityLike?
    
    fun countByPost(post: CommunityPost): Long
} 