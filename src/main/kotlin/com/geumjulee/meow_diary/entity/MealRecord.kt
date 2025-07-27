package com.geumjulee.meow_diary.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

@Entity
@Table(name = "meal_records")
class MealRecord : BaseEntity() {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
    
    @NotNull
    @Column(name = "meal_date", nullable = false)
    var mealDate: LocalDateTime = LocalDateTime.now()
    
    @Column(name = "food_name", nullable = false)
    var foodName: String = ""
    
    @Column(name = "food_type")
    @Enumerated(EnumType.STRING)
    var foodType: FoodType = FoodType.DRY_FOOD
    
    @Column(name = "amount")
    var amount: Double? = null
    
    @Column(name = "unit")
    var unit: String? = null
    
    @Column(name = "calories")
    var calories: Int? = null
    
    @Column(name = "ingredients", columnDefinition = "TEXT")
    var ingredients: String? = null
    
    @Column(name = "feeding_time")
    @Enumerated(EnumType.STRING)
    var feedingTime: FeedingTime = FeedingTime.REGULAR
    
    @Column(name = "notes", columnDefinition = "TEXT")
    var notes: String? = null
    
    @Column(name = "appetite_level")
    @Enumerated(EnumType.STRING)
    var appetiteLevel: AppetiteLevel = AppetiteLevel.NORMAL
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id", nullable = false)
    var cat: Cat? = null
    
    @OneToMany(mappedBy = "mealRecord", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val images: MutableList<Image> = mutableListOf()
}

enum class FoodType {
    DRY_FOOD, WET_FOOD, TREATS, HOMEMADE, RAW_FOOD
}

enum class FeedingTime {
    BREAKFAST, LUNCH, DINNER, SNACK, REGULAR
}

enum class AppetiteLevel {
    VERY_LOW, LOW, NORMAL, HIGH, VERY_HIGH
} 