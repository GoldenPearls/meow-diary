package com.geumjulee.meow_diary.entity

import jakarta.persistence.*

@Entity
@Table(name = "community_likes")
class CommunityLike : BaseEntity() {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    var post: CommunityPost? = null
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null
    
    @Column(name = "like_type")
    @Enumerated(EnumType.STRING)
    var likeType: LikeType = LikeType.LIKE
}

enum class LikeType {
    LIKE, LOVE, LAUGH, WOW, SAD, ANGRY
} 