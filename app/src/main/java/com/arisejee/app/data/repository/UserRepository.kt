package com.arisejee.app.data.repository

import com.arisejee.app.data.db.dao.UserDao
import com.arisejee.app.data.db.entity.UserEntity
import com.arisejee.app.data.model.Rank
import kotlinx.coroutines.flow.Flow

class UserRepository(private val dao: UserDao) {
    fun getUser(): Flow<UserEntity?> = dao.getUser()

    suspend fun ensureUserExists() {
        if (dao.getUserSync() == null) dao.insertOrUpdate(UserEntity())
    }

    suspend fun addXp(amount: Int) {
        val user = dao.getUserSync() ?: return
        val newXp = user.xp + amount
        val newRank = Rank.fromXp(newXp)
        val newLevel = 1 + newXp / 100
        dao.updateXpAndRank(newXp, newLevel, newRank.name)
    }

    suspend fun recordAnswer(correct: Boolean) {
        dao.incrementStats(if (correct) 1 else 0)
    }

    suspend fun updateStreak(streak: Int, date: String) = dao.updateStreak(streak, date)
    suspend fun updateUsername(name: String) = dao.updateUsername(name)
    suspend fun updateProfilePic(uri: String) = dao.updateProfilePic(uri)
    suspend fun updateTimetable(uri: String?) = dao.updateTimetable(uri)
}
