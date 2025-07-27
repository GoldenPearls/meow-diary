package com.geumjulee.meow_diary.controller

import com.geumjulee.meow_diary.dto.*
import com.geumjulee.meow_diary.entity.LikeType
import com.geumjulee.meow_diary.service.CommunityService
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/community")
class CommunityController(
    private val communityService: CommunityService
) {
    
    @PostMapping("/posts")
    fun createPost(
        @RequestParam userId: Long,
        @Valid @RequestBody request: PostCreateRequest
    ): ResponseEntity<PostResponse> {
        val post = communityService.createPost(userId, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(post)
    }
    
    @GetMapping("/posts/{id}")
    fun getPostById(@PathVariable id: Long): ResponseEntity<PostResponse> {
        val post = communityService.getPostById(id)
        return ResponseEntity.ok(post)
    }
    
    @GetMapping("/posts")
    fun getAllPosts(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<org.springframework.data.domain.Page<PostResponse>> {
        val pageable: Pageable = PageRequest.of(page, size)
        val posts = communityService.getAllPosts(pageable)
        return ResponseEntity.ok(posts)
    }
    
    @GetMapping("/posts/category/{category}")
    fun getPostsByCategory(
        @PathVariable category: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<org.springframework.data.domain.Page<PostResponse>> {
        val pageable: Pageable = PageRequest.of(page, size)
        val posts = communityService.getPostsByCategory(category, pageable)
        return ResponseEntity.ok(posts)
    }
    
    @GetMapping("/posts/search")
    fun searchPosts(
        @RequestParam keyword: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<org.springframework.data.domain.Page<PostResponse>> {
        val pageable: Pageable = PageRequest.of(page, size)
        val posts = communityService.searchPosts(keyword, pageable)
        return ResponseEntity.ok(posts)
    }
    
    @GetMapping("/posts/user/{userId}")
    fun getPostsByUserId(
        @PathVariable userId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<org.springframework.data.domain.Page<PostResponse>> {
        val pageable: Pageable = PageRequest.of(page, size)
        val posts = communityService.getPostsByUserId(userId, pageable)
        return ResponseEntity.ok(posts)
    }
    
    @GetMapping("/posts/popular")
    fun getPopularPosts(
        @RequestParam(defaultValue = "5") minLikes: Int,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<org.springframework.data.domain.Page<PostResponse>> {
        val pageable: Pageable = PageRequest.of(page, size)
        val posts = communityService.getPopularPosts(minLikes, pageable)
        return ResponseEntity.ok(posts)
    }
    
    @PutMapping("/posts/{id}")
    fun updatePost(
        @PathVariable id: Long,
        @RequestParam userId: Long,
        @Valid @RequestBody request: PostUpdateRequest
    ): ResponseEntity<PostResponse> {
        val post = communityService.updatePost(id, userId, request)
        return ResponseEntity.ok(post)
    }
    
    @DeleteMapping("/posts/{id}")
    fun deletePost(@PathVariable id: Long, @RequestParam userId: Long): ResponseEntity<Void> {
        communityService.deletePost(id, userId)
        return ResponseEntity.noContent().build()
    }
    
    @PostMapping("/posts/{postId}/like")
    fun likePost(
        @PathVariable postId: Long,
        @RequestParam userId: Long,
        @RequestParam(defaultValue = "LIKE") likeType: LikeType
    ): ResponseEntity<Void> {
        communityService.likePost(postId, userId, likeType)
        return ResponseEntity.ok().build()
    }
    
    @DeleteMapping("/posts/{postId}/like")
    fun unlikePost(@PathVariable postId: Long, @RequestParam userId: Long): ResponseEntity<Void> {
        communityService.unlikePost(postId, userId)
        return ResponseEntity.ok().build()
    }
} 