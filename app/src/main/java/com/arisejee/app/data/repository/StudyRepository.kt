package com.arisejee.app.data.repository

import com.arisejee.app.data.db.dao.StudySessionDao
import com.arisejee.app.data.db.entity.StudySessionEntity
import kotlinx.coroutines.flow.Flow

class StudyRepository(private val dao: StudySessionDao) {
    suspend fun saveSession(date: String, startMillis: Long, endMillis: Long, durationMin: Int) {
        dao.insert(StudySessionEntity(date = date, startTimeMillis = startMillis, endTimeMillis = endMillis, durationMinutes = durationMin))
    }
    fun getTodayMinutes(date: String): Flow<Int> = dao.getTotalMinutesByDate(date)
    fun getWeekMinutes(start: String, end: String): Flow<Int> = dao.getTotalMinutesBetween(start, end)
    fun getTotalMinutes(): Flow<Int> = dao.getTotalMinutesAll()
}
