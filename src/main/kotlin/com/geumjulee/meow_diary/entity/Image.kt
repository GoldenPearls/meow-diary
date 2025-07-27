package com.geumjulee.meow_diary.entity

import jakarta.persistence.*

@Entity
@Table(name = "images")
class Image : BaseEntity() {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
    
    @Column(name = "file_name", nullable = false)
    var fileName: String = ""
    
    @Column(name = "original_file_name", nullable = false)
    var originalFileName: String = ""
    
    @Column(name = "file_path", nullable = false)
    var filePath: String = ""
    
    @Column(name = "file_size")
    var fileSize: Long = 0
    
    @Column(name = "content_type")
    var contentType: String = ""
    
    @Column(name = "image_type")
    @Enumerated(EnumType.STRING)
    var imageType: ImageType = ImageType.PROFILE
    
    @Column(name = "description")
    var description: String? = null
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id")
    var cat: Cat? = null
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_record_id")
    var healthRecord: HealthRecord? = null
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meal_record_id")
    var mealRecord: MealRecord? = null
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_post_id")
    var communityPost: CommunityPost? = null
}

enum class ImageType {
    PROFILE, HEALTH, MEAL, COMMUNITY, GENERAL
} 