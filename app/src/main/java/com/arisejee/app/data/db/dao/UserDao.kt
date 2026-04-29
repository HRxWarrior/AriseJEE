package com.arisejee.app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arisejee.app.data.db.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id = 1")
    fun getUser(): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE id = 1")
    suspend fun getUserSync(): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(user: UserEntity)

    @Query("UPDATE users SET xp = :xp, level = :level, rank = :rank WHERE id = 1")
    suspend fun updateXpAndRank(xp: Int, level: Int, rank: String)

    @Query("UPDATE users SET streak = :streak, lastActiveDate = :date WHERE id = 1")
    suspend fun updateStreak(streak: Int, date: String)

    @Query("UPDATE users SET totalQuestionsSolved = totalQuestionsSolved + 1, correctAnswers = correctAnswers + :correct WHERE id = 1")
    suspend fun incrementStats(correct: Int)

    @Query("UPDATE users SET username = :name WHERE id = 1")
    suspend fun updateUsername(name: String)

    @Query("UPDATE users SET profilePicUri = :uri WHERE id = 1")
    suspend fun updateProfilePic(uri: String)

    @Query("UPDATE users SET timetableUri = :uri WHERE id = 1")
    suspend fun updateTimetable(uri: String?)
}
