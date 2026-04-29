package com.arisejee.app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.arisejee.app.data.db.entity.StudySessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StudySessionDao {
    @Insert
    suspend fun insert(session: StudySessionEntity)

    @Query("SELECT * FROM study_sessions WHERE date = :date ORDER BY startTimeMillis")
    fun getSessionsByDate(date: String): Flow<List<StudySessionEntity>>

    @Query("SELECT COALESCE(SUM(durationMinutes),0) FROM study_sessions WHERE date = :date")
    fun getTotalMinutesByDate(date: String): Flow<Int>

    @Query("SELECT COALESCE(SUM(durationMinutes),0) FROM study_sessions WHERE date BETWEEN :startDate AND :endDate")
    fun getTotalMinutesBetween(startDate: String, endDate: String): Flow<Int>

    @Query("SELECT COALESCE(SUM(durationMinutes),0) FROM study_sessions")
    fun getTotalMinutesAll(): Flow<Int>
}
