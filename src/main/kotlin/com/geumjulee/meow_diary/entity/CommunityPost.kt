package com.geumjulee.meow_diary.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "community_posts")
class CommunityPost : BaseEntity() {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    
    @NotBlank
    @Column(name = "title", nullable = false)
    var title: String = ""
    
    @NotBlank
    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    var content: String = ""
    
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    var category: PostCategory = PostCategory.GENERAL
    
    @Column(name = "view_count")
    var viewCount: Int = 0
    
    @Column(name = "like_count")
    var likeCount: Int = 0
    
    @Column(name = "comment_count")
    var commentCount: Int = 0
    
    @Column(name = "is_pinned")
    var isPinned: Boolean = false
    
    @Column(name = "is_deleted")
    var isDeleted: Boolean = false
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null
    
    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var comments: MutableList<CommunityComment> = mutableListOf()
    
    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var likes: MutableList<CommunityLike> = mutableListOf()
    
    @OneToMany(mappedBy = "communityPost", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var images: MutableList<Image> = mutableListOf()
}

enum class PostCategory {
    GENERAL, HEALTH, FOOD, BEHAVIOR, ADOPTION, LOST_FOUND, VETERINARY, TRAINING
} 