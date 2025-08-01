package com.geumjulee.meow_diary.controller

import com.geumjulee.meow_diary.dto.*
import com.geumjulee.meow_diary.service.CommunityService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import jakarta.validation.Valid

@RestController
@RequestMapping("/api/community")
class CommunityController(
    private val communityService: CommunityService
) {

    @GetMapping("/posts")
    fun getPosts(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(required = false) search: String?,
        @RequestParam(required = false) category: String?
    ): ResponseEntity<Map<String, Any>> {
        val pageRequest = PageRequest.of(page, size)
        val posts = communityService.getPosts(pageRequest, search, category)
        
        return ResponseEntity.ok(mapOf(
            "content" to posts.content,
            "totalPages" to posts.totalPages,
            "currentPage" to posts.number,
            "totalElements" to posts.totalElements
        ))
    }

    @PostMapping("/posts")
    fun createPost(
        @RequestParam title: String,
        @RequestParam content: String,
        @RequestParam category: String,
        @RequestParam(required = false) image: MultipartFile?
    ): ResponseEntity<PostResponse> {
        val request = PostCreateRequest(
            title = title,
            content = content,
            category = com.geumjulee.meow_diary.entity.PostCategory.valueOf(category.uppercase())
        )
        
        val post = communityService.createPost(request, image)
        return ResponseEntity.status(HttpStatus.CREATED).body(post)
    }

    @GetMapping("/posts/{postId}/comments")
    fun getComments(@PathVariable postId: Long): ResponseEntity<List<CommentResponse>> {
        val comments = communityService.getComments(postId)
        return ResponseEntity.ok(comments)
    }

    @PostMapping("/posts/{postId}/comments")
    fun createComment(
        @PathVariable postId: Long,
        @Valid @RequestBody request: CommentCreateRequest
    ): ResponseEntity<CommentResponse> {
        val comment = communityService.createComment(postId, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(comment)
    }

    @PostMapping("/posts/{postId}/like")
    fun likePost(@PathVariable postId: Long): ResponseEntity<Map<String, String>> {
        communityService.likePost(postId)
        return ResponseEntity.ok(mapOf("message" to "좋아요가 반영되었습니다."))
    }

    @DeleteMapping("/posts/{postId}/like")
    fun unlikePost(@PathVariable postId: Long): ResponseEntity<Map<String, String>> {
        communityService.unlikePost(postId)
        return ResponseEntity.ok(mapOf("message" to "좋아요가 취소되었습니다."))
    }
} 