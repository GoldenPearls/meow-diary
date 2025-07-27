package com.geumjulee.meow_diary.service

import com.geumjulee.meow_diary.dto.DailyRecordDto
import com.geumjulee.meow_diary.entity.DailyRecord
import com.geumjulee.meow_diary.repository.DailyRecordRepository
import com.geumjulee.meow_diary.repository.UserRepository
import com.geumjulee.meow_diary.repository.CatRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class DailyRecordService(
    private val dailyRecordRepository: DailyRecordRepository,
    private val userRepository: UserRepository,
    private val catRepository: CatRepository
) {

    fun createDailyRecord(dailyRecordDto: DailyRecordDto): DailyRecord {
        val user = userRepository.findById(dailyRecordDto.userId!!)
            .orElseThrow { RuntimeException("사용자를 찾을 수 없습니다. ID: ${dailyRecordDto.userId}") }
        val cat = catRepository.findById(dailyRecordDto.catId!!)
            .orElseThrow { RuntimeException("고양이를 찾을 수 없습니다. ID: ${dailyRecordDto.catId}") }
        
        val dailyRecord = dailyRecordDto.toEntity().apply {
            this.user = user
            this.cat = cat
        }
        
        return dailyRecordRepository.save(dailyRecord)
    }

    fun getDailyRecordById(id: Long): DailyRecord {
        return dailyRecordRepository.findById(id)
            .orElseThrow { RuntimeException("일일 기록을 찾을 수 없습니다. ID: $id") }
    }

    fun getDailyRecordsByUserId(userId: Long): List<DailyRecord> {
        return dailyRecordRepository.findByUserId(userId)
    }

    fun getDailyRecordsByCatId(catId: Long): List<DailyRecord> {
        return dailyRecordRepository.findByCatId(catId)
    }

    fun getDailyRecordsByUserIdAndCatId(userId: Long, catId: Long): List<DailyRecord> {
        return dailyRecordRepository.findByUserIdAndCatIdOrderByDateDesc(userId, catId)
    }

    fun getDailyRecordsByDateRange(userId: Long, catId: Long, startDate: LocalDate, endDate: LocalDate): List<DailyRecord> {
        return dailyRecordRepository.findByUserIdAndCatIdAndDateRange(userId, catId, startDate, endDate)
    }

    fun getDailyRecordsByDate(date: LocalDate): List<DailyRecord> {
        return dailyRecordRepository.findByRecordDate(date)
    }

    fun getDailyRecordsByUserIdAndDate(userId: Long, date: LocalDate): List<DailyRecord> {
        return dailyRecordRepository.findByUserIdAndRecordDate(userId, date)
    }

    fun getImportantRecordsByUserId(userId: Long): List<DailyRecord> {
        return dailyRecordRepository.findImportantRecordsByUserId(userId)
    }

    fun getDailyRecordsByCatIdAndMood(catId: Long, mood: DailyRecord.Mood): List<DailyRecord> {
        return dailyRecordRepository.findByCatIdAndMood(catId, mood)
    }

    fun getDailyRecordsByCatIdAndActivityType(catId: Long, activityType: DailyRecord.ActivityType): List<DailyRecord> {
        return dailyRecordRepository.findByCatIdAndActivityType(catId, activityType)
    }

    fun getDailyRecordsByUserIdAndTag(userId: Long, tag: String): List<DailyRecord> {
        return dailyRecordRepository.findByUserIdAndTagContaining(userId, tag)
    }

    fun getRecentDailyRecordsByCatId(catId: Long, pageable: Pageable): Page<DailyRecord> {
        return dailyRecordRepository.findRecentRecordsByCatId(catId, pageable)
    }

    fun updateDailyRecord(id: Long, dailyRecordDto: DailyRecordDto): DailyRecord {
        val existingRecord = getDailyRecordById(id)
        
        existingRecord.apply {
            this.title = dailyRecordDto.title
            this.content = dailyRecordDto.content
            this.mood = dailyRecordDto.mood
            this.activityType = dailyRecordDto.activityType
            this.weather = dailyRecordDto.weather
            this.temperature = dailyRecordDto.temperature
            this.specialNotes = dailyRecordDto.specialNotes
            this.isImportant = dailyRecordDto.isImportant
            this.tags = dailyRecordDto.tags
        }
        
        return dailyRecordRepository.save(existingRecord)
    }

    fun deleteDailyRecord(id: Long) {
        val dailyRecord = getDailyRecordById(id)
        dailyRecordRepository.delete(dailyRecord)
    }

    fun getRecordCountByCatIdAndDateRange(catId: Long, startDate: LocalDate, endDate: LocalDate): Long {
        return dailyRecordRepository.countRecordsByCatIdAndDateRange(catId, startDate, endDate)
    }

    fun getMoodStatisticsByCatId(catId: Long): Map<DailyRecord.Mood, Long> {
        val records = getDailyRecordsByCatId(catId)
        return records.filter { it.mood != null }
            .groupBy { it.mood!! }
            .mapValues { it.value.size.toLong() }
    }

    fun getActivityStatisticsByCatId(catId: Long): Map<DailyRecord.ActivityType, Long> {
        val records = getDailyRecordsByCatId(catId)
        return records.filter { it.activityType != null }
            .groupBy { it.activityType!! }
            .mapValues { it.value.size.toLong() }
    }
} 