package com.geumjulee.meow_diary.service

import com.geumjulee.meow_diary.entity.Image
import com.geumjulee.meow_diary.entity.ImageType
import com.geumjulee.meow_diary.repository.ImageRepository
import com.geumjulee.meow_diary.util.FileUtil
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

@Service
class ImageService(
    private val imageRepository: ImageRepository,
    private val fileUtil: FileUtil
) {

    fun uploadImage(
        file: MultipartFile,
        imageType: ImageType,
        entityId: Long?,
        description: String?
    ): Image {
        // 파일 유효성 검사
        if (!fileUtil.isValidImageFile(file)) {
            throw IllegalArgumentException("유효하지 않은 이미지 파일입니다.")
        }
        
        // 파일 저장
        val subDirectory = when (imageType) {
            ImageType.PROFILE -> "profiles"
            ImageType.HEALTH -> "health"
            ImageType.MEAL -> "meals"
            ImageType.COMMUNITY -> "community"
            ImageType.GENERAL -> "general"
        }
        
        val savedFilePath = fileUtil.saveFile(file, subDirectory)
        
        // 이미지 엔티티 생성
        val image = Image().apply {
            fileName = fileUtil.generateUniqueFileName(file.originalFilename)
            originalFileName = file.originalFilename ?: ""
            filePath = savedFilePath
            fileSize = file.size
            contentType = file.contentType ?: ""
            this.imageType = imageType
            this.description = description
            // TODO: 적절한 엔티티 ID 설정 로직 필요
        }
        
        return imageRepository.save(image)
    }

    fun getImageById(id: Long): Image {
        return imageRepository.findById(id)
            .orElseThrow { RuntimeException("이미지를 찾을 수 없습니다. ID: $id") }
    }

    fun getImagesByCatId(catId: Long): List<Image> {
        return imageRepository.findByCatId(catId)
    }

    fun getImagesByType(imageType: ImageType): List<Image> {
        return imageRepository.findByImageType(imageType)
    }

    fun deleteImage(id: Long) {
        val image = getImageById(id)
        
        // 파일 시스템에서 삭제
        fileUtil.deleteFile(image.filePath)
        
        // 데이터베이스에서 삭제
        imageRepository.delete(image)
    }

    fun getImagesByUser(userId: Long): List<Image> {
        return imageRepository.findByUserId(userId)
    }

    fun getImagesByHealthRecord(healthRecordId: Long): List<Image> {
        return imageRepository.findByHealthRecordId(healthRecordId)
    }

    fun getImagesByMealRecord(mealRecordId: Long): List<Image> {
        return imageRepository.findByMealRecordId(mealRecordId)
    }
} 