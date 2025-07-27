package com.geumjulee.meow_diary.repository

import com.geumjulee.meow_diary.entity.AiQuery
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface AiQueryRepository : JpaRepository<AiQuery, Long> {
    
    @Query("SELECT aq FROM AiQuery aq WHERE aq.user.id = :userId")
    fun findByUserId(@Param("userId") userId: Long): List<AiQuery>
    
    @Query("SELECT aq FROM AiQuery aq WHERE aq.cat.id = :catId")
    fun findByCatId(@Param("catId") catId: Long): List<AiQuery>
    
    fun findByStatus(status: String): List<AiQuery>
    
    fun findByQueryType(queryType: String): List<AiQuery>
    
    @Query("SELECT aq FROM AiQuery aq WHERE aq.user.id = :userId AND aq.status = :status")
    fun findByUserIdAndStatus(
        @Param("userId") userId: Long,
        @Param("status") status: String
    ): List<AiQuery>
    
    @Query("SELECT aq FROM AiQuery aq WHERE aq.cat.id = :catId AND aq.queryType = :queryType")
    fun findByCatIdAndQueryType(
        @Param("catId") catId: Long,
        @Param("queryType") queryType: String
    ): List<AiQuery>
    
    @Query("SELECT aq FROM AiQuery aq WHERE aq.status = :status ORDER BY aq.createdAt DESC")
    fun findByStatusOrderByCreatedAtDesc(@Param("status") status: String): List<AiQuery>
} 