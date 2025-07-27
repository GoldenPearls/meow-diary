package com.geumjulee.meow_diary.controller

import com.geumjulee.meow_diary.dto.ApiResponse
import com.geumjulee.meow_diary.dto.DailyRecordDto
import com.geumjulee.meow_diary.entity.DailyRecord
import com.geumjulee.meow_diary.service.DailyRecordService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/daily-records")
class DailyRecordController(
    private val dailyRecordService: DailyRecordService
) {

    @PostMapping
    fun createDailyRecord(@RequestBody @Valid dailyRecordDto: DailyRecordDto): ResponseEntity<ApiResponse<DailyRecordDto>> {
        val dailyRecord = dailyRecordService.createDailyRecord(dailyRecordDto)
        return ResponseEntity.ok(ApiResponse.success(dailyRecord.toDto()))
    }

    @GetMapping("/{id}")
    fun getDailyRecord(@PathVariable id: Long): ResponseEntity<ApiResponse<DailyRecordDto>> {
        val dailyRecord = dailyRecordService.getDailyRecordById(id)
        return ResponseEntity.ok(ApiResponse.success(dailyRecord.toDto()))
    }

    @GetMapping("/user/{userId}")
    fun getDailyRecordsByUserId(@PathVariable userId: Long): ResponseEntity<ApiResponse<List<DailyRecordDto>>> {
        val dailyRecords = dailyRecordService.getDailyRecordsByUserId(userId)
        return ResponseEntity.ok(ApiResponse.success(dailyRecords.map { it.toDto() }))
    }

    @GetMapping("/cat/{catId}")
    fun getDailyRecordsByCatId(@PathVariable catId: Long): ResponseEntity<ApiResponse<List<DailyRecordDto>>> {
        val dailyRecords = dailyRecordService.getDailyRecordsByCatId(catId)
        return ResponseEntity.ok(ApiResponse.success(dailyRecords.map { it.toDto() }))
    }

    @GetMapping("/user/{userId}/cat/{catId}")
    fun getDailyRecordsByUserIdAndCatId(
        @PathVariable userId: Long,
        @PathVariable catId: Long
    ): ResponseEntity<ApiResponse<List<DailyRecordDto>>> {
        val dailyRecords = dailyRecordService.getDailyRecordsByUserIdAndCatId(userId, catId)
        return ResponseEntity.ok(ApiResponse.success(dailyRecords.map { it.toDto() }))
    }

    @GetMapping("/user/{userId}/cat/{catId}/date-range")
    fun getDailyRecordsByDateRange(
        @PathVariable userId: Long,
        @PathVariable catId: Long,
        @RequestParam startDate: LocalDate,
        @RequestParam endDate: LocalDate
    ): ResponseEntity<ApiResponse<List<DailyRecordDto>>> {
        val dailyRecords = dailyRecordService.getDailyRecordsByDateRange(userId, catId, startDate, endDate)
        return ResponseEntity.ok(ApiResponse.success(dailyRecords.map { it.toDto() }))
    }

    @GetMapping("/date/{date}")
    fun getDailyRecordsByDate(@PathVariable date: LocalDate): ResponseEntity<ApiResponse<List<DailyRecordDto>>> {
        val dailyRecords = dailyRecordService.getDailyRecordsByDate(date)
        return ResponseEntity.ok(ApiResponse.success(dailyRecords.map { it.toDto() }))
    }

    @GetMapping("/user/{userId}/date/{date}")
    fun getDailyRecordsByUserIdAndDate(
        @PathVariable userId: Long,
        @PathVariable date: LocalDate
    ): ResponseEntity<ApiResponse<List<DailyRecordDto>>> {
        val dailyRecords = dailyRecordService.getDailyRecordsByUserIdAndDate(userId, date)
        return ResponseEntity.ok(ApiResponse.success(dailyRecords.map { it.toDto() }))
    }

    @GetMapping("/user/{userId}/important")
    fun getImportantRecordsByUserId(@PathVariable userId: Long): ResponseEntity<ApiResponse<List<DailyRecordDto>>> {
        val dailyRecords = dailyRecordService.getImportantRecordsByUserId(userId)
        return ResponseEntity.ok(ApiResponse.success(dailyRecords.map { it.toDto() }))
    }

    @GetMapping("/cat/{catId}/mood/{mood}")
    fun getDailyRecordsByCatIdAndMood(
        @PathVariable catId: Long,
        @PathVariable mood: DailyRecord.Mood
    ): ResponseEntity<ApiResponse<List<DailyRecordDto>>> {
        val dailyRecords = dailyRecordService.getDailyRecordsByCatIdAndMood(catId, mood)
        return ResponseEntity.ok(ApiResponse.success(dailyRecords.map { it.toDto() }))
    }

    @GetMapping("/cat/{catId}/activity/{activityType}")
    fun getDailyRecordsByCatIdAndActivityType(
        @PathVariable catId: Long,
        @PathVariable activityType: DailyRecord.ActivityType
    ): ResponseEntity<ApiResponse<List<DailyRecordDto>>> {
        val dailyRecords = dailyRecordService.getDailyRecordsByCatIdAndActivityType(catId, activityType)
        return ResponseEntity.ok(ApiResponse.success(dailyRecords.map { it.toDto() }))
    }

    @GetMapping("/user/{userId}/tag/{tag}")
    fun getDailyRecordsByUserIdAndTag(
        @PathVariable userId: Long,
        @PathVariable tag: String
    ): ResponseEntity<ApiResponse<List<DailyRecordDto>>> {
        val dailyRecords = dailyRecordService.getDailyRecordsByUserIdAndTag(userId, tag)
        return ResponseEntity.ok(ApiResponse.success(dailyRecords.map { it.toDto() }))
    }

    @GetMapping("/cat/{catId}/recent")
    fun getRecentDailyRecordsByCatId(
        @PathVariable catId: Long,
        pageable: Pageable
    ): ResponseEntity<ApiResponse<Page<DailyRecordDto>>> {
        val dailyRecords = dailyRecordService.getRecentDailyRecordsByCatId(catId, pageable)
        return ResponseEntity.ok(ApiResponse.success(dailyRecords.map { it.toDto() }))
    }

    @PutMapping("/{id}")
    fun updateDailyRecord(
        @PathVariable id: Long,
        @RequestBody @Valid dailyRecordDto: DailyRecordDto
    ): ResponseEntity<ApiResponse<DailyRecordDto>> {
        val dailyRecord = dailyRecordService.updateDailyRecord(id, dailyRecordDto)
        return ResponseEntity.ok(ApiResponse.success(dailyRecord.toDto()))
    }

    @DeleteMapping("/{id}")
    fun deleteDailyRecord(@PathVariable id: Long): ResponseEntity<ApiResponse<String>> {
        dailyRecordService.deleteDailyRecord(id)
        return ResponseEntity.ok(ApiResponse.success("일일 기록이 성공적으로 삭제되었습니다."))
    }

    @GetMapping("/cat/{catId}/statistics/mood")
    fun getMoodStatisticsByCatId(@PathVariable catId: Long): ResponseEntity<ApiResponse<Map<DailyRecord.Mood, Long>>> {
        val moodStatistics = dailyRecordService.getMoodStatisticsByCatId(catId)
        return ResponseEntity.ok(ApiResponse.success(moodStatistics))
    }

    @GetMapping("/cat/{catId}/statistics/activity")
    fun getActivityStatisticsByCatId(@PathVariable catId: Long): ResponseEntity<ApiResponse<Map<DailyRecord.ActivityType, Long>>> {
        val activityStatistics = dailyRecordService.getActivityStatisticsByCatId(catId)
        return ResponseEntity.ok(ApiResponse.success(activityStatistics))
    }

    @GetMapping("/cat/{catId}/count")
    fun getRecordCountByCatIdAndDateRange(
        @PathVariable catId: Long,
        @RequestParam startDate: LocalDate,
        @RequestParam endDate: LocalDate
    ): ResponseEntity<ApiResponse<Long>> {
        val count = dailyRecordService.getRecordCountByCatIdAndDateRange(catId, startDate, endDate)
        return ResponseEntity.ok(ApiResponse.success(count))
    }
} 