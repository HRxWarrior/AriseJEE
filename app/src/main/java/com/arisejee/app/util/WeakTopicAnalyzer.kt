package com.arisejee.app.util

import com.arisejee.app.data.db.entity.QuizResultEntity
import com.arisejee.app.data.model.WeakTopic

object WeakTopicAnalyzer {
    fun analyze(results: List<QuizResultEntity>): List<WeakTopic> {
        return results.groupBy { "${'$'}{it.subject}|${'$'}{it.chapter}" }
            .mapNotNull { (key, items) ->
                val parts = key.split("|")
                if (parts.size < 2) return@mapNotNull null
                val wrong = items.count { !it.isCorrect }
                val total = items.size
                if (total < 3) return@mapNotNull null
                val wrongRate = wrong.toFloat() / total
                val avgTime = items.map { it.timeTakenSeconds }.average().toFloat()
                val severity = when {
                    wrongRate > 0.6f || avgTime > 120f -> "HIGH"
                    wrongRate > 0.35f || avgTime > 90f -> "MEDIUM"
                    wrongRate > 0.2f -> "LOW"
                    else -> return@mapNotNull null
                }
                WeakTopic(parts[0], parts[1], wrong, avgTime, total, severity)
            }
            .sortedByDescending { it.wrongCount }
    }
}
