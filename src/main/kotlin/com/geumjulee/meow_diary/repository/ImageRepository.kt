package com.geumjulee.meow_diary.repository

import com.geumjulee.meow_diary.entity.Image
import com.geumjulee.meow_diary.entity.ImageType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ImageRepository : JpaRepository<Image, Long> {

    @Query("SELECT i FROM Image i WHERE i.cat.id = :catId")
    fun findByCatId(@Param("catId") catId: Long): List<Image>

    fun findByImageType(imageType: ImageType): List<Image>

    @Query("SELECT i FROM Image i WHERE i.user.id = :userId")
    fun findByUserId(@Param("userId") userId: Long): List<Image>

    @Query("SELECT i FROM Image i WHERE i.healthRecord.id = :healthRecordId")
    fun findByHealthRecordId(@Param("healthRecordId") healthRecordId: Long): List<Image>

    @Query("SELECT i FROM Image i WHERE i.mealRecord.id = :mealRecordId")
    fun findByMealRecordId(@Param("mealRecordId") mealRecordId: Long): List<Image>

    @Query("SELECT i FROM Image i WHERE i.communityPost.id = :communityPostId")
    fun findByCommunityPostId(@Param("communityPostId") communityPostId: Long): List<Image>

    @Query("SELECT i FROM Image i WHERE i.cat.id = :catId AND i.imageType = :imageType")
    fun findByCatIdAndImageType(@Param("catId") catId: Long, @Param("imageType") imageType: ImageType): List<Image>

    @Query("SELECT i FROM Image i WHERE i.user.id = :userId AND i.imageType = :imageType")
    fun findByUserIdAndImageType(@Param("userId") userId: Long, @Param("imageType") imageType: ImageType): List<Image>

    @Query("SELECT i FROM Image i WHERE i.cat.id = :catId ORDER BY i.createdAt DESC")
    fun findRecentImagesByCatId(@Param("catId") catId: Long): List<Image>

    @Query("SELECT i FROM Image i WHERE i.imageType = :imageType ORDER BY i.createdAt DESC")
    fun findRecentImagesByType(@Param("imageType") imageType: ImageType): List<Image>
} 