package com.arisejee.app.util

import com.arisejee.app.data.model.Difficulty
import com.arisejee.app.data.model.Rank

object XpCalculator {
    fun calculate(difficulty: Difficulty, rank: Rank, isCorrect: Boolean, streakCount: Int): Int {
        if (!isCorrect) return -(difficulty.baseXp / 4)
        var xp = difficulty.baseXp
        if (difficulty == Difficulty.EASY) xp = (xp * rank.xpMultiplier).toInt()
        val streakBonus = when {
            streakCount >= 10 -> 1.5f
            streakCount >= 5 -> 1.25f
            streakCount >= 3 -> 1.1f
            else -> 1.0f
        }
        return (xp * streakBonus).toInt().coerceAtLeast(1)
    }
}
