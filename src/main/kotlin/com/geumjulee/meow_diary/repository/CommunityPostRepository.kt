package com.geumjulee.meow_diary.repository

import com.geumjulee.meow_diary.entity.CommunityPost
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CommunityPostRepository : JpaRepository<CommunityPost, Long> {
    
    fun findByUserId(userId: Long): List<CommunityPost>
    
    fun findByCategory(category: String): List<CommunityPost>
    
    fun findByIsPinnedTrue(): List<CommunityPost>
    
    fun findByIsDeletedFalse(): List<CommunityPost>
    
    @Query("SELECT cp FROM CommunityPost cp WHERE cp.isDeleted = false ORDER BY cp.createdAt DESC")
    fun findAllActivePosts(pageable: Pageable): Page<CommunityPost>
    
    @Query("SELECT cp FROM CommunityPost cp WHERE cp.category = :category AND cp.isDeleted = false ORDER BY cp.createdAt DESC")
    fun findByCategoryAndNotDeleted(@Param("category") category: String, pageable: Pageable): Page<CommunityPost>
    
    @Query("SELECT cp FROM CommunityPost cp WHERE cp.title LIKE %:keyword% OR cp.content LIKE %:keyword% AND cp.isDeleted = false")
    fun searchByKeyword(@Param("keyword") keyword: String, pageable: Pageable): Page<CommunityPost>
    
    @Query("SELECT cp FROM CommunityPost cp WHERE cp.user.id = :userId AND cp.isDeleted = false ORDER BY cp.createdAt DESC")
    fun findByUserIdAndNotDeleted(@Param("userId") userId: Long, pageable: Pageable): Page<CommunityPost>
    
    @Query("SELECT cp FROM CommunityPost cp WHERE cp.likeCount >= :minLikes AND cp.isDeleted = false ORDER BY cp.likeCount DESC")
    fun findPopularPosts(@Param("minLikes") minLikes: Int, pageable: Pageable): Page<CommunityPost>
} 