package com.geumjulee.meow_diary.service

import com.geumjulee.meow_diary.dto.*
import com.geumjulee.meow_diary.entity.CommunityComment
import com.geumjulee.meow_diary.entity.CommunityLike
import com.geumjulee.meow_diary.entity.CommunityPost
import com.geumjulee.meow_diary.entity.LikeType
import com.geumjulee.meow_diary.repository.CommunityPostRepository
import com.geumjulee.meow_diary.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CommunityService(
    private val communityPostRepository: CommunityPostRepository,
    private val userRepository: UserRepository
) {
    
    fun createPost(userId: Long, request: PostCreateRequest): PostResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("사용자를 찾을 수 없습니다") }
        
        val post = CommunityPost().apply {
            title = request.title
            content = request.content
            category = request.category
            this.user = user
            viewCount = 0
            likeCount = 0
            commentCount = 0
            isPinned = false
            isDeleted = false
        }
        
        val savedPost = communityPostRepository.save(post)
        return PostResponse.from(savedPost)
    }
    
    @Transactional(readOnly = true)
    fun getPostById(id: Long): PostResponse {
        val post = communityPostRepository.findById(id)
            .orElseThrow { IllegalArgumentException("게시글을 찾을 수 없습니다") }
        
        if (post.isDeleted) {
            throw IllegalArgumentException("삭제된 게시글입니다")
        }
        
        // 조회수 증가
        post.viewCount++
        communityPostRepository.save(post)
        
        return PostResponse.from(post)
    }
    
    @Transactional(readOnly = true)
    fun getAllPosts(pageable: Pageable): Page<PostResponse> {
        return communityPostRepository.findAllActivePosts(pageable)
            .map { PostResponse.from(it) }
    }
    
    @Transactional(readOnly = true)
    fun getPostsByCategory(category: String, pageable: Pageable): Page<PostResponse> {
        return communityPostRepository.findByCategoryAndNotDeleted(category, pageable)
            .map { PostResponse.from(it) }
    }
    
    @Transactional(readOnly = true)
    fun searchPosts(keyword: String, pageable: Pageable): Page<PostResponse> {
        return communityPostRepository.searchByKeyword(keyword, pageable)
            .map { PostResponse.from(it) }
    }
    
    @Transactional(readOnly = true)
    fun getPostsByUserId(userId: Long, pageable: Pageable): Page<PostResponse> {
        return communityPostRepository.findByUserIdAndNotDeleted(userId, pageable)
            .map { PostResponse.from(it) }
    }
    
    @Transactional(readOnly = true)
    fun getPopularPosts(minLikes: Int, pageable: Pageable): Page<PostResponse> {
        return communityPostRepository.findPopularPosts(minLikes, pageable)
            .map { PostResponse.from(it) }
    }
    
    fun updatePost(id: Long, userId: Long, request: PostUpdateRequest): PostResponse {
        val post = communityPostRepository.findById(id)
            .orElseThrow { IllegalArgumentException("게시글을 찾을 수 없습니다") }
        
        if (post.user?.id != userId) {
            throw IllegalArgumentException("게시글을 수정할 권한이 없습니다")
        }
        
        if (post.isDeleted) {
            throw IllegalArgumentException("삭제된 게시글입니다")
        }
        
        request.title?.let { post.title = it }
        request.content?.let { post.content = it }
        request.category?.let { post.category = it }
        
        val updatedPost = communityPostRepository.save(post)
        return PostResponse.from(updatedPost)
    }
    
    fun deletePost(id: Long, userId: Long) {
        val post = communityPostRepository.findById(id)
            .orElseThrow { IllegalArgumentException("게시글을 찾을 수 없습니다") }
        
        if (post.user?.id != userId) {
            throw IllegalArgumentException("게시글을 삭제할 권한이 없습니다")
        }
        
        post.isDeleted = true
        communityPostRepository.save(post)
    }
    
    fun likePost(postId: Long, userId: Long, likeType: LikeType = LikeType.LIKE) {
        val post = communityPostRepository.findById(postId)
            .orElseThrow { IllegalArgumentException("게시글을 찾을 수 없습니다") }
        
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("사용자를 찾을 수 없습니다") }
        
        // 이미 좋아요를 눌렀는지 확인
        val existingLike = post.likes.find { it.user?.id == userId }
        if (existingLike != null) {
            throw IllegalArgumentException("이미 좋아요를 눌렀습니다")
        }
        
        val like = CommunityLike().apply {
            this.post = post
            this.user = user
            this.likeType = likeType
        }
        
        post.likes.add(like)
        post.likeCount++
        communityPostRepository.save(post)
    }
    
    fun unlikePost(postId: Long, userId: Long) {
        val post = communityPostRepository.findById(postId)
            .orElseThrow { IllegalArgumentException("게시글을 찾을 수 없습니다") }
        
        val like = post.likes.find { it.user?.id == userId }
            ?: throw IllegalArgumentException("좋아요를 누르지 않았습니다")
        
        post.likes.remove(like)
        post.likeCount--
        communityPostRepository.save(post)
    }
} 