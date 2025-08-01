package com.geumjulee.meow_diary.repository

import com.geumjulee.meow_diary.entity.CommunityComment
import com.geumjulee.meow_diary.entity.CommunityPost
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommunityCommentRepository : JpaRepository<CommunityComment, Long> {
    
    fun findByPostOrderByCreatedAtDesc(post: CommunityPost): List<CommunityComment>
    
    fun countByPost(post: CommunityPost): Long
} 