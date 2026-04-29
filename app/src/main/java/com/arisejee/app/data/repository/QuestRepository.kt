package com.arisejee.app.data.repository

import com.arisejee.app.data.db.dao.DailyQuestDao
import com.arisejee.app.data.db.entity.DailyQuestEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class QuestRepository(private val dao: DailyQuestDao) {
    fun getQuests(date: String): Flow<List<DailyQuestEntity>> = dao.getQuestsByDate(date)
    suspend fun ensureQuestsExist(date: String) {
        val existing = dao.getQuestsByDate(date).first()
        if (existing.isEmpty()) {
            listOf(
                DailyQuestEntity(title="First Blood", description="Solve 5 questions", targetCount=5, xpReward=50, date=date),
                DailyQuestEntity(title="Streak Hunter", description="Get 3 correct in a row", targetCount=3, xpReward=75, date=date),
                DailyQuestEntity(title="Hard Mode", description="Solve 2 hard questions", targetCount=2, xpReward=100, date=date),
                DailyQuestEntity(title="Study Warrior", description="Study for 30 minutes", targetCount=30, xpReward=60, date=date),
            ).forEach { dao.insertOrUpdate(it) }
        }
    }
    suspend fun updateProgress(id: Long, newCount: Int, target: Int) {
        dao.updateProgress(id, newCount, newCount >= target)
    }
}
