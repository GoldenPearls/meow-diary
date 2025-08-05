package com.geumjulee.meow_diary.controller

import com.geumjulee.meow_diary.dto.CatCreateRequest
import com.geumjulee.meow_diary.dto.CatResponse
import com.geumjulee.meow_diary.dto.CatUpdateRequest
import com.geumjulee.meow_diary.service.CatService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/cats")
class CatController(
    private val catService: CatService
) {
    
    @PostMapping
    fun createCat(
        @RequestParam userId: Long, 
        @RequestParam name: String,
        @RequestParam(required = false) breed: String?,
        @RequestParam(required = false) color: String?,
        @RequestParam(required = false) birthDate: String?,
        @RequestParam(required = false) weight: Double?,
        @RequestParam(required = false) gender: String?,
        @RequestParam(required = false) isNeutered: Boolean?,
        @RequestParam(required = false) description: String?,
        @RequestParam(required = false) image: MultipartFile?
    ): ResponseEntity<CatResponse> {
        // TODO: 실제로는 토큰에서 사용자 ID를 추출해야 함
        val request = CatCreateRequest(
            name = name,
            breed = breed,
            color = color,
            birthDate = birthDate?.let { java.time.LocalDate.parse(it) },
            weight = weight,
            gender = gender?.let { com.geumjulee.meow_diary.entity.CatGender.valueOf(it.uppercase()) },
            isNeutered = isNeutered ?: false,
            description = description
        )
        val cat = catService.createCat(userId, request, image)
        return ResponseEntity.status(HttpStatus.CREATED).body(cat)
    }
    
    @PostMapping("/json")
    fun createCatWithJson(
        @RequestParam userId: Long,
        @Valid @RequestBody request: CatCreateRequest
    ): ResponseEntity<CatResponse> {
        val cat = catService.createCat(userId, request, null)
        return ResponseEntity.status(HttpStatus.CREATED).body(cat)
    }
    
    @GetMapping("/{id}")
    fun getCatById(@PathVariable id: Long): ResponseEntity<CatResponse> {
        val cat = catService.getCatById(id)
        return ResponseEntity.ok(cat)
    }
    
    @GetMapping("/user/{userId}")
    fun getCatsByUserId(@PathVariable userId: Long): ResponseEntity<List<CatResponse>> {
        val cats = catService.getCatsByUserId(userId)
        return ResponseEntity.ok(cats)
    }
    
    @PutMapping("/{id}")
    fun updateCat(@PathVariable id: Long, @Valid @RequestBody request: CatUpdateRequest): ResponseEntity<CatResponse> {
        val cat = catService.updateCat(id, request)
        return ResponseEntity.ok(cat)
    }
    
    @DeleteMapping("/{id}")
    fun deleteCat(@PathVariable id: Long): ResponseEntity<Void> {
        catService.deleteCat(id)
        return ResponseEntity.noContent().build()
    }
    
    @GetMapping("/search")
    fun searchCatsByName(@RequestParam name: String): ResponseEntity<List<CatResponse>> {
        val cats = catService.searchCatsByName(name)
        return ResponseEntity.ok(cats)
    }
    
    @GetMapping("/breed/{breed}")
    fun getCatsByBreed(@PathVariable breed: String): ResponseEntity<List<CatResponse>> {
        val cats = catService.getCatsByBreed(breed)
        return ResponseEntity.ok(cats)
    }
    
    @GetMapping("/gender/{gender}")
    fun getCatsByGender(@PathVariable gender: String): ResponseEntity<List<CatResponse>> {
        val cats = catService.getCatsByGender(gender)
        return ResponseEntity.ok(cats)
    }
    
    @GetMapping("/neutered/{isNeutered}")
    fun getCatsByNeuteredStatus(@PathVariable isNeutered: Boolean): ResponseEntity<List<CatResponse>> {
        val cats = catService.getCatsByNeuteredStatus(isNeutered)
        return ResponseEntity.ok(cats)
    }
} 