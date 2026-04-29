package com.arisejee.app.ui.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object Subjects : Screen("subjects")
    object Chapters : Screen("chapters/{subject}") { fun createRoute(subject: String) = "chapters/$subject" }
    object Quiz : Screen("quiz/{subject}/{chapter}/{examType}") { fun createRoute(s: String, c: String, e: String) = "quiz/$s/$c/$e" }
    object QuizResult : Screen("quiz_result/{score}/{total}/{xpEarned}") { fun createRoute(score: Int, total: Int, xp: Int) = "quiz_result/$score/$total/$xp" }
    object Profile : Screen("profile")
    object StudyTimer : Screen("study_timer")
    object FocusMode : Screen("focus_mode")
    object WeakTopics : Screen("weak_topics")
    object DailyQuests : Screen("daily_quests")
}
