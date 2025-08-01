package com.geumjulee.meow_diary.service

import com.geumjulee.meow_diary.dto.*
import com.geumjulee.meow_diary.entity.*
import com.geumjulee.meow_diary.repository.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

@Service
@Transactional
class CommunityService(
    private val communityPostRepository: CommunityPostRepository,
    private val communityCommentRepository: CommunityCommentRepository,
    private val communityLikeRepository: CommunityLikeRepository,
    private val userRepository: UserRepository,
    private val imageRepository: ImageRepository
) {

    fun getPosts(pageable: Pageable, search: String?, category: String?): Page<PostResponse> {
        val posts = when {
            !search.isNullOrBlank() && !category.isNullOrBlank() -> {
                communityPostRepository.findByTitleContainingAndCategory(search, category, pageable)
            }
            !search.isNullOrBlank() -> {
                communityPostRepository.findByTitleContainingOrContentContaining(search, search, pageable)
            }
            !category.isNullOrBlank() -> {
                communityPostRepository.findByCategory(category, pageable)
            }
            else -> {
                communityPostRepository.findAllByOrderByCreatedAtDesc(pageable)
            }
        }
        
        return posts.map { post ->
            val commentCount = communityCommentRepository.countByPost(post)
            val likeCount = communityLikeRepository.countByPost(post)
            PostResponse.from(post)
        }
    }

    fun createPost(request: PostCreateRequest, image: MultipartFile?): PostResponse {
        // TODO: 실제로는 토큰에서 사용자 ID를 추출해야 함
        val userId = 1L // 임시로 고정
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("사용자를 찾을 수 없습니다.") }

        val post = CommunityPost().apply {
            title = request.title
            content = request.content
            category = request.category
            this.user = user
        }

        val savedPost = communityPostRepository.save(post)

        // 이미지 처리
        image?.let { img ->
            val imageEntity = Image().apply {
                originalFileName = img.originalFilename ?: ""
                fileName = "${System.currentTimeMillis()}_${img.originalFilename}"
                filePath = "/uploads/community/${savedPost.id}/"
                contentType = img.contentType ?: "image/jpeg"
                fileSize = img.size
                this.user = user
                communityPost = savedPost
            }
            imageRepository.save(imageEntity)
        }

        return PostResponse.from(savedPost)
    }

    fun getComments(postId: Long): List<CommentResponse> {
        val post = communityPostRepository.findById(postId)
            .orElseThrow { IllegalArgumentException("게시글을 찾을 수 없습니다.") }
        
        return communityCommentRepository.findByPostOrderByCreatedAtDesc(post)
            .map { comment ->
                CommentResponse(
                    id = comment.id,
                    content = comment.content,
                    likeCount = 0, // TODO: 댓글 좋아요 기능 구현
                    userId = comment.user?.id ?: 0,
                    username = comment.user?.username ?: "",
                    userFullName = "${comment.user?.firstName ?: ""} ${comment.user?.lastName ?: ""}".trim(),
                    parentCommentId = comment.parentComment?.id,
                    createdAt = comment.createdAt,
                    updatedAt = comment.updatedAt
                )
            }
    }

    fun createComment(postId: Long, request: CommentCreateRequest): CommentResponse {
        // TODO: 실제로는 토큰에서 사용자 ID를 추출해야 함
        val userId = 1L // 임시로 고정
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("사용자를 찾을 수 없습니다.") }
        
        val post = communityPostRepository.findById(postId)
            .orElseThrow { IllegalArgumentException("게시글을 찾을 수 없습니다.") }

        val comment = CommunityComment().apply {
            content = request.content
            this.post = post
            this.user = user
        }

        val savedComment = communityCommentRepository.save(comment)
        return CommentResponse(
            id = savedComment.id,
            content = savedComment.content,
            likeCount = 0,
            userId = savedComment.user?.id ?: 0,
            username = savedComment.user?.username ?: "",
            userFullName = "${savedComment.user?.firstName ?: ""} ${savedComment.user?.lastName ?: ""}".trim(),
            parentCommentId = savedComment.parentComment?.id,
            createdAt = savedComment.createdAt,
            updatedAt = savedComment.updatedAt
        )
    }

    fun likePost(postId: Long) {
        // TODO: 실제로는 토큰에서 사용자 ID를 추출해야 함
        val userId = 1L // 임시로 고정
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("사용자를 찾을 수 없습니다.") }
        
        val post = communityPostRepository.findById(postId)
            .orElseThrow { IllegalArgumentException("게시글을 찾을 수 없습니다.") }

        val existingLike = communityLikeRepository.findByPostAndUser(post, user)
        if (existingLike == null) {
            val like = CommunityLike().apply {
                this.post = post
                this.user = user
                likeType = LikeType.LIKE
            }
            communityLikeRepository.save(like)
        }
    }

    fun unlikePost(postId: Long) {
        // TODO: 실제로는 토큰에서 사용자 ID를 추출해야 함
        val userId = 1L // 임시로 고정
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("사용자를 찾을 수 없습니다.") }
        
        val post = communityPostRepository.findById(postId)
            .orElseThrow { IllegalArgumentException("게시글을 찾을 수 없습니다.") }

        val like = communityLikeRepository.findByPostAndUser(post, user)
        like?.let { communityLikeRepository.delete(it) }
    }
} 