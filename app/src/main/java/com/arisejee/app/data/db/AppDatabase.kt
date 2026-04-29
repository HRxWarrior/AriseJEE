package com.arisejee.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arisejee.app.data.db.dao.DailyQuestDao
import com.arisejee.app.data.db.dao.QuestionDao
import com.arisejee.app.data.db.dao.QuizResultDao
import com.arisejee.app.data.db.dao.StudySessionDao
import com.arisejee.app.data.db.dao.UserDao
import com.arisejee.app.data.db.entity.DailyQuestEntity
import com.arisejee.app.data.db.entity.QuestionEntity
import com.arisejee.app.data.db.entity.QuizResultEntity
import com.arisejee.app.data.db.entity.StudySessionEntity
import com.arisejee.app.data.db.entity.UserEntity

@Database(
    entities = [
        QuestionEntity::class,
        UserEntity::class,
        StudySessionEntity::class,
        QuizResultEntity::class,
        DailyQuestEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    abstract fun userDao(): UserDao
    abstract fun studySessionDao(): StudySessionDao
    abstract fun quizResultDao(): QuizResultDao
    abstract fun dailyQuestDao(): DailyQuestDao
}
