package com.arisejee.app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arisejee.app.data.db.entity.DailyQuestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyQuestDao {
    @Query("SELECT * FROM daily_quests WHERE date = :date")
    fun getQuestsByDate(date: String): Flow<List<DailyQuestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(quest: DailyQuestEntity)

    @Query("UPDATE daily_quests SET currentCount = :count, isCompleted = :done WHERE id = :id")
    suspend fun updateProgress(id: Long, count: Int, done: Boolean)
}
