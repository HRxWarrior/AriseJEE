package com.arisejee.app.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_results")
data class QuizResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val subject: String,
    val chapter: String,
    val examType: String,
    val questionId: Long,
    val isCorrect: Boolean,
    val timeTakenSeconds: Int,
    val difficulty: String = "MEDIUM",
    val timestamp: Long = System.currentTimeMillis()
)
