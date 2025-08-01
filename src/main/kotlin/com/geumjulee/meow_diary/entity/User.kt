package com.geumjulee.meow_diary.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(name = "users")
class User : BaseEntity() {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    
    @NotBlank
    @Size(min = 3, max = 20)
    @Column(name = "username", unique = true, nullable = false)
    var username: String = ""
    
    @NotBlank
    @Email
    @Column(name = "email", unique = true, nullable = false)
    var email: String = ""
    
    @NotBlank
    @Size(min = 6, max = 100)
    @Column(name = "password", nullable = false)
    var password: String = ""
    
    @NotBlank
    @Size(min = 2, max = 20)
    @Column(name = "nickname", unique = true, nullable = false)
    var nickname: String = ""
    
    @Column(name = "email_verified", nullable = false)
    var emailVerified: Boolean = false
    
    @Column(name = "email_verification_token")
    var emailVerificationToken: String? = null
    
    @NotBlank
    @Column(name = "first_name", nullable = false)
    var firstName: String = ""
    
    @NotBlank
    @Column(name = "last_name", nullable = false)
    var lastName: String = ""
    
    @Column(name = "phone")
    var phone: String? = null
    
    @Column(name = "address")
    var address: String? = null
    
    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true
    
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    var role: UserRole = UserRole.USER
    
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var cats: MutableList<Cat> = mutableListOf()
    
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var communityPosts: MutableList<CommunityPost> = mutableListOf()
    
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var communityComments: MutableList<CommunityComment> = mutableListOf()
    
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var communityLikes: MutableList<CommunityLike> = mutableListOf()
    
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var aiQueries: MutableList<AiQuery> = mutableListOf()
}

enum class UserRole {
    USER, ADMIN
} 