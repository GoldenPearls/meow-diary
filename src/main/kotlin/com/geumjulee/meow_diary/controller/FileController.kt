package com.geumjulee.meow_diary.controller

import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.nio.file.Path
import java.nio.file.Paths

@RestController
@RequestMapping("/api/files")
class FileController {
    
    @GetMapping("/uploads/**")
    fun serveFile(@RequestParam file: String): ResponseEntity<Resource> {
        val filePath = Paths.get("uploads").resolve(file)
        val resource = UrlResource(filePath.toUri())
        
        return if (resource.exists() && resource.isReadable) {
            val contentType = determineContentType(file)
            ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"${resource.filename}\"")
                .body(resource)
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    private fun determineContentType(fileName: String): String {
        return when (fileName.substringAfterLast(".", "").lowercase()) {
            "jpg", "jpeg" -> "image/jpeg"
            "png" -> "image/png"
            "gif" -> "image/gif"
            "webp" -> "image/webp"
            else -> "application/octet-stream"
        }
    }
} 