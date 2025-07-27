package com.geumjulee.meow_diary.controller

import com.geumjulee.meow_diary.dto.ApiResponse
import com.geumjulee.meow_diary.dto.ImageDto
import com.geumjulee.meow_diary.entity.Image
import com.geumjulee.meow_diary.entity.ImageType
import com.geumjulee.meow_diary.service.ImageService
import com.geumjulee.meow_diary.util.FileUtil
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/images")
class ImageController(
    private val imageService: ImageService,
    private val fileUtil: FileUtil
) {

    @PostMapping("/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadImage(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("imageType") imageType: ImageType,
        @RequestParam("entityId", required = false) entityId: Long?,
        @RequestParam("description", required = false) description: String?
    ): ResponseEntity<ApiResponse<ImageDto>> {
        val image = imageService.uploadImage(file, imageType, entityId, description)
        return ResponseEntity.ok(ApiResponse.success(image.toDto()))
    }

    @GetMapping("/{id}")
    fun getImage(@PathVariable id: Long): ResponseEntity<ApiResponse<ImageDto>> {
        val image = imageService.getImageById(id)
        return ResponseEntity.ok(ApiResponse.success(image.toDto()))
    }

    @GetMapping("/cat/{catId}")
    fun getImagesByCat(@PathVariable catId: Long): ResponseEntity<ApiResponse<List<ImageDto>>> {
        val images = imageService.getImagesByCatId(catId)
        return ResponseEntity.ok(ApiResponse.success(images.map { it.toDto() }))
    }

    @GetMapping("/type/{imageType}")
    fun getImagesByType(@PathVariable imageType: ImageType): ResponseEntity<ApiResponse<List<ImageDto>>> {
        val images = imageService.getImagesByType(imageType)
        return ResponseEntity.ok(ApiResponse.success(images.map { it.toDto() }))
    }

    @DeleteMapping("/{id}")
    fun deleteImage(@PathVariable id: Long): ResponseEntity<ApiResponse<String>> {
        imageService.deleteImage(id)
        return ResponseEntity.ok(ApiResponse.success("이미지가 성공적으로 삭제되었습니다."))
    }
} 