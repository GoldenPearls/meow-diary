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
    val id: Long = 0
    
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
    val cats: MutableList<Cat> = mutableListOf()
    
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val communityPosts: MutableList<CommunityPost> = mutableListOf()
    
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val communityComments: MutableList<CommunityComment> = mutableListOf()
    
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val communityLikes: MutableList<CommunityLike> = mutableListOf()
    
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val aiQueries: MutableList<AiQuery> = mutableListOf()
}

enum class UserRole {
    USER, ADMIN
} 