package com.arisejee.app.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "study_sessions")
data class StudySessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,
    val startTimeMillis: Long,
    val endTimeMillis: Long,
    val durationMinutes: Int
)
