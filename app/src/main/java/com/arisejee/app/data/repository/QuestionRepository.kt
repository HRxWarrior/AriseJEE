package com.arisejee.app.data.repository

import com.arisejee.app.data.db.dao.QuestionDao
import com.arisejee.app.data.db.entity.QuestionEntity
import com.arisejee.app.data.provider.LocalQuestionProvider
import com.arisejee.app.data.provider.QuestionProvider
import kotlinx.coroutines.flow.Flow

class QuestionRepository(
    private val dao: QuestionDao,
    private val provider: QuestionProvider = LocalQuestionProvider()
) {
    fun getQuestions(subject: String, chapter: String, examType: String): Flow<List<QuestionEntity>> =
        dao.getQuestions(subject, chapter, examType)

    suspend fun getCount(subject: String, chapter: String, examType: String): Int =
        dao.getCount(subject, chapter, examType)

    suspend fun loadQuestions(subject: String, chapter: String, examType: String, count: Int = 15) {
        val questions = provider.fetchQuestions(subject, chapter, examType, count)
        if (questions.isNotEmpty()) dao.insertAll(questions)
    }

    suspend fun ensureQuestionsLoaded(subject: String, chapter: String, examType: String) {
        if (dao.getCount(subject, chapter, examType) == 0) {
            loadQuestions(subject, chapter, examType)
        }
    }
}
