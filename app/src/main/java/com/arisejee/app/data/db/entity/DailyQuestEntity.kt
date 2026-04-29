package com.arisejee.app.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_quests")
data class DailyQuestEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val targetCount: Int,
    val currentCount: Int = 0,
    val xpReward: Int,
    val date: String,
    val isCompleted: Boolean = false
)
