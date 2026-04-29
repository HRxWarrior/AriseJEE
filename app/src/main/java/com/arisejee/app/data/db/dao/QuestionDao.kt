package com.arisejee.app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arisejee.app.data.db.entity.QuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {
    @Query("SELECT * FROM questions WHERE subject = :subject AND chapter = :chapter AND examType = :examType")
    fun getQuestions(subject: String, chapter: String, examType: String): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM questions WHERE subject = :subject AND chapter = :chapter AND examType = :examType AND difficulty = :difficulty LIMIT :limit")
    suspend fun getByDifficulty(subject: String, chapter: String, examType: String, difficulty: String, limit: Int = 5): List<QuestionEntity>

    @Query("SELECT COUNT(*) FROM questions WHERE subject = :subject AND chapter = :chapter AND examType = :examType")
    suspend fun getCount(subject: String, chapter: String, examType: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<QuestionEntity>)
}
