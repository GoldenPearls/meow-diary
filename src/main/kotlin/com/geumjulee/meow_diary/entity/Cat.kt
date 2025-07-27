package com.geumjulee.meow_diary.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

@Entity
@Table(name = "cats")
class Cat : BaseEntity() {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
    
    @NotBlank
    @Column(name = "name", nullable = false)
    var name: String = ""
    
    @Column(name = "breed")
    var breed: String? = null
    
    @Column(name = "color")
    var color: String? = null
    
    @NotNull
    @Column(name = "birth_date")
    var birthDate: LocalDate? = null
    
    @Column(name = "weight")
    var weight: Double? = null
    
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    var gender: CatGender? = null
    
    @Column(name = "is_neutered")
    var isNeutered: Boolean = false
    
    @Column(name = "profile_image_url")
    var profileImageUrl: String? = null
    
    @Column(name = "description", columnDefinition = "TEXT")
    var description: String? = null
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null
    
    @OneToMany(mappedBy = "cat", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val healthRecords: MutableList<HealthRecord> = mutableListOf()
    
    @OneToMany(mappedBy = "cat", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val mealRecords: MutableList<MealRecord> = mutableListOf()
    
    @OneToMany(mappedBy = "cat", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val images: MutableList<Image> = mutableListOf()
}

enum class CatGender {
    MALE, FEMALE
} 