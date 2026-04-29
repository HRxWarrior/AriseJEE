package com.arisejee.app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.arisejee.app.data.db.entity.QuizResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizResultDao {
    @Insert
    suspend fun insert(result: QuizResultEntity)

    @Query("SELECT * FROM quiz_results ORDER BY timestamp DESC")
    fun getAllResults(): Flow<List<QuizResultEntity>>

    @Query("SELECT * FROM quiz_results WHERE subject = :subject AND chapter = :chapter ORDER BY timestamp DESC")
    fun getByChapter(subject: String, chapter: String): Flow<List<QuizResultEntity>>
}
