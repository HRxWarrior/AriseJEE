package com.arisejee.app.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int = 1,
    val username: String = "Hunter",
    val profilePicUri: String? = null,
    val xp: Int = 0,
    val level: Int = 1,
    val rank: String = "E",
    val streak: Int = 0,
    val lastActiveDate: String = "",
    val totalQuestionsSolved: Int = 0,
    val correctAnswers: Int = 0,
    val timetableUri: String? = null
)
