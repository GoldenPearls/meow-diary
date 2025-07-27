package com.geumjulee.meow_diary.dto

import com.geumjulee.meow_diary.entity.CommunityPost
import com.geumjulee.meow_diary.entity.PostCategory
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

data class PostCreateRequest(
    @field:NotBlank(message = "제목은 필수입니다")
    val title: String,
    
    @field:NotBlank(message = "내용은 필수입니다")
    val content: String,
    
    val category: PostCategory = PostCategory.GENERAL
)

data class PostUpdateRequest(
    val title: String? = null,
    val content: String? = null,
    val category: PostCategory? = null
)

data class PostResponse(
    val id: Long,
    val title: String,
    val content: String,
    val category: PostCategory,
    val viewCount: Int,
    val likeCount: Int,
    val commentCount: Int,
    val isPinned: Boolean,
    val userId: Long,
    val username: String,
    val userNickname: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(post: CommunityPost): PostResponse {
            return PostResponse(
                id = post.id,
                title = post.title,
                content = post.content,
                category = post.category,
                viewCount = post.viewCount,
                likeCount = post.likeCount,
                commentCount = post.commentCount,
                isPinned = post.isPinned,
                userId = post.user?.id ?: 0,
                username = post.user?.username ?: "",
                userNickname = post.user?.nickname,
                createdAt = post.createdAt,
                updatedAt = post.updatedAt
            )
        }
    }
}

data class CommentCreateRequest(
    @field:NotBlank(message = "댓글 내용은 필수입니다")
    val content: String,
    
    val parentCommentId: Long? = null
)

data class CommentUpdateRequest(
    val content: String
)

data class CommentResponse(
    val id: Long,
    val content: String,
    val likeCount: Int,
    val userId: Long,
    val username: String,
    val userNickname: String?,
    val parentCommentId: Long?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)

data class PostSummaryResponse(
    val id: Long,
    val title: String,
    val category: PostCategory,
    val viewCount: Int,
    val likeCount: Int,
    val commentCount: Int,
    val username: String,
    val userNickname: String?,
    val createdAt: LocalDateTime
) 