package com.geumjulee.meow_diary.util

import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@Component
class FileUtil {
    
    private val uploadDir = "uploads"
    
    init {
        createUploadDirectory()
    }
    
    private fun createUploadDirectory() {
        val uploadPath = Paths.get(uploadDir)
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath)
            } catch (e: IOException) {
                throw RuntimeException("업로드 디렉토리를 생성할 수 없습니다", e)
            }
        }
    }
    
    fun saveFile(file: MultipartFile, subDirectory: String): String {
        val fileName = generateUniqueFileName(file.originalFilename)
        val subDirPath = Paths.get(uploadDir, subDirectory)
        
        if (!Files.exists(subDirPath)) {
            Files.createDirectories(subDirPath)
        }
        
        val filePath = subDirPath.resolve(fileName)
        file.transferTo(filePath.toFile())
        
        return filePath.toString()
    }
    
    fun deleteFile(filePath: String): Boolean {
        return try {
            val path = Paths.get(filePath)
            Files.deleteIfExists(path)
        } catch (e: IOException) {
            false
        }
    }
    
    fun getFileExtension(fileName: String): String {
        return if (fileName.contains(".")) {
            fileName.substringAfterLast(".")
        } else {
            ""
        }
    }
    
    fun isValidImageFile(file: MultipartFile): Boolean {
        val allowedExtensions = setOf("jpg", "jpeg", "png", "gif", "webp")
        val extension = getFileExtension(file.originalFilename ?: "").lowercase()
        return allowedExtensions.contains(extension)
    }
    
    fun getFileSize(file: MultipartFile): Long {
        return file.size
    }
    
    fun generateUniqueFileName(originalFileName: String?): String {
        val extension = originalFileName?.let { getFileExtension(it) } ?: ""
        val uuid = UUID.randomUUID().toString()
        return if (extension.isNotEmpty()) {
            "$uuid.$extension"
        } else {
            uuid
        }
    }
    
    fun getFileUrl(filePath: String): String {
        return "/api/files/$filePath"
    }
    
    fun uploadImage(file: MultipartFile, subDirectory: String): String {
        if (!isValidImageFile(file)) {
            throw IllegalArgumentException("지원하지 않는 이미지 형식입니다. jpg, jpeg, png, gif, webp 파일만 업로드 가능합니다.")
        }
        
        if (file.size > 5 * 1024 * 1024) { // 5MB 제한
            throw IllegalArgumentException("이미지 크기는 5MB 이하여야 합니다.")
        }
        
        val fileName = generateUniqueFileName(file.originalFilename)
        val subDirPath = Paths.get(uploadDir, subDirectory)
        
        if (!Files.exists(subDirPath)) {
            Files.createDirectories(subDirPath)
        }
        
        val filePath = subDirPath.resolve(fileName)
        file.transferTo(filePath.toFile())
        
        return "/uploads/$subDirectory/$fileName"
    }
} 