package com.geumjulee.meow_diary.service

import com.geumjulee.meow_diary.dto.CatCreateRequest
import com.geumjulee.meow_diary.dto.CatResponse
import com.geumjulee.meow_diary.dto.CatUpdateRequest
import com.geumjulee.meow_diary.entity.Cat
import com.geumjulee.meow_diary.repository.CatRepository
import com.geumjulee.meow_diary.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CatService(
    private val catRepository: CatRepository,
    private val userRepository: UserRepository
) {
    
    fun createCat(userId: Long, request: CatCreateRequest): CatResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("사용자를 찾을 수 없습니다") }
        
        val cat = Cat().apply {
            name = request.name
            breed = request.breed
            color = request.color
            birthDate = request.birthDate
            weight = request.weight
            gender = request.gender
            isNeutered = request.isNeutered
            description = request.description
            this.user = user
        }
        
        val savedCat = catRepository.save(cat)
        return CatResponse.from(savedCat)
    }
    
    @Transactional(readOnly = true)
    fun getCatById(id: Long): CatResponse {
        val cat = catRepository.findById(id)
            .orElseThrow { IllegalArgumentException("고양이를 찾을 수 없습니다") }
        return CatResponse.from(cat)
    }
    
    @Transactional(readOnly = true)
    fun getCatsByUserId(userId: Long): List<CatResponse> {
        return catRepository.findByUserId(userId).map { CatResponse.from(it) }
    }
    
    fun updateCat(id: Long, request: CatUpdateRequest): CatResponse {
        val cat = catRepository.findById(id)
            .orElseThrow { IllegalArgumentException("고양이를 찾을 수 없습니다") }
        
        request.name?.let { cat.name = it }
        request.breed?.let { cat.breed = it }
        request.color?.let { cat.color = it }
        request.birthDate?.let { cat.birthDate = it }
        request.weight?.let { cat.weight = it }
        request.gender?.let { cat.gender = it }
        request.isNeutered?.let { cat.isNeutered = it }
        request.profileImageUrl?.let { cat.profileImageUrl = it }
        request.description?.let { cat.description = it }
        
        val updatedCat = catRepository.save(cat)
        return CatResponse.from(updatedCat)
    }
    
    fun deleteCat(id: Long) {
        val cat = catRepository.findById(id)
            .orElseThrow { IllegalArgumentException("고양이를 찾을 수 없습니다") }
        
        catRepository.delete(cat)
    }
    
    @Transactional(readOnly = true)
    fun searchCatsByName(name: String): List<CatResponse> {
        return catRepository.findByNameContainingIgnoreCase(name).map { CatResponse.from(it) }
    }
    
    @Transactional(readOnly = true)
    fun getCatsByBreed(breed: String): List<CatResponse> {
        return catRepository.findByBreed(breed).map { CatResponse.from(it) }
    }
    
    @Transactional(readOnly = true)
    fun getCatsByGender(gender: String): List<CatResponse> {
        return catRepository.findByGender(gender).map { CatResponse.from(it) }
    }
    
    @Transactional(readOnly = true)
    fun getCatsByNeuteredStatus(isNeutered: Boolean): List<CatResponse> {
        return catRepository.findByNeuteredStatus(isNeutered).map { CatResponse.from(it) }
    }
} 