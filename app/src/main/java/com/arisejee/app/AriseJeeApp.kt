package com.arisejee.app

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.arisejee.app.data.db.AppDatabase
import com.arisejee.app.data.db.entity.QuestionEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AriseJeeApp : Application() {
    val database: AppDatabase by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "arise_jee_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Check if we have JEE_ADVANCED questions; if not, reload all
                val advCount = database.questionDao().getCount("Physics", "Kinematics", "JEE_ADVANCED")
                if (advCount == 0) {
                    Log.i("AriseJeeApp", "No Advanced questions found, loading from assets...")
                    loadQuestionsFromAssets()
                }
            } catch (e: Exception) {
                Log.e("AriseJeeApp", "Error checking questions", e)
            }
        }
    }

    private suspend fun loadQuestionsFromAssets() {
        try {
            val json = assets.open("questions.json").bufferedReader().readText()
            val type = object : TypeToken<List<QuestionData>>() {}.type
            val questions: List<QuestionData> = Gson().fromJson(json, type)
            val entities = questions.map {
                QuestionEntity(
                    subject = it.subject, chapter = it.chapter,
                    questionText = it.questionText, optionA = it.optionA,
                    optionB = it.optionB, optionC = it.optionC, optionD = it.optionD,
                    correctAnswer = it.correctAnswer, solution = it.solution,
                    difficulty = it.difficulty, examType = it.examType
                )
            }
            database.questionDao().insertAll(entities)
            Log.i("AriseJeeApp", "Loaded ${entities.size} questions from assets")
        } catch (e: Exception) {
            Log.e("AriseJeeApp", "Failed to load questions", e)
        }
    }

    data class QuestionData(
        val subject: String = "", val chapter: String = "",
        val questionText: String = "", val optionA: String = "",
        val optionB: String = "", val optionC: String = "",
        val optionD: String = "", val correctAnswer: Int = 0,
        val solution: String = "", val difficulty: String = "MEDIUM",
        val examType: String = "JEE_MAINS"
    )
}
