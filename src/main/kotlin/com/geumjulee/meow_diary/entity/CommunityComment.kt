package com.geumjulee.meow_diary.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "community_comments")
class CommunityComment : BaseEntity() {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
    
    @NotBlank
    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    var content: String = ""
    
    @Column(name = "like_count")
    var likeCount: Int = 0
    
    @Column(name = "is_deleted")
    var isDeleted: Boolean = false
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    var post: CommunityPost? = null
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    var parentComment: CommunityComment? = null
    
    @OneToMany(mappedBy = "parentComment", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val replies: MutableList<CommunityComment> = mutableListOf()
} 