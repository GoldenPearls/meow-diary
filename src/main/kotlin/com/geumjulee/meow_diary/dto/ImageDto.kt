package com.geumjulee.meow_diary.dto

import com.geumjulee.meow_diary.entity.ImageType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ImageDto(
    val id: Long? = null,
    val fileName: String = "",
    val originalFileName: String = "",
    val filePath: String = "",
    val fileSize: Long = 0,
    val contentType: String = "",
    val imageType: ImageType = ImageType.GENERAL,
    val description: String? = null,
    val userId: Long? = null,
    val catId: Long? = null,
    val healthRecordId: Long? = null,
    val mealRecordId: Long? = null,
    val communityPostId: Long? = null
) 