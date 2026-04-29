package com.arisejee.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.arisejee.app.ui.screens.ChapterListScreen
import com.arisejee.app.ui.screens.DailyQuestScreen
import com.arisejee.app.ui.screens.DashboardScreen
import com.arisejee.app.ui.screens.FocusModeScreen
import com.arisejee.app.ui.screens.ProfileScreen
import com.arisejee.app.ui.screens.QuizResultScreen
import com.arisejee.app.ui.screens.QuizScreen
import com.arisejee.app.ui.screens.StudyTimerScreen
import com.arisejee.app.ui.screens.SubjectListScreen
import com.arisejee.app.ui.screens.WeakTopicsScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Dashboard.route) {
        composable(Screen.Dashboard.route) { DashboardScreen(navController) }
        composable(Screen.Subjects.route) { SubjectListScreen(navController) }
        composable(Screen.Chapters.route, arguments = listOf(navArgument("subject") { type = NavType.StringType })) { entry ->
            ChapterListScreen(navController, entry.arguments?.getString("subject") ?: "")
        }
        composable(Screen.Quiz.route, arguments = listOf(navArgument("subject"){type=NavType.StringType}, navArgument("chapter"){type=NavType.StringType}, navArgument("examType"){type=NavType.StringType})) { entry ->
            val a = entry.arguments; QuizScreen(navController, a?.getString("subject")?:"", a?.getString("chapter")?:"", a?.getString("examType")?:"JEE_MAINS")
        }
        composable(Screen.QuizResult.route, arguments = listOf(navArgument("score"){type=NavType.IntType}, navArgument("total"){type=NavType.IntType}, navArgument("xpEarned"){type=NavType.IntType})) { entry ->
            val a = entry.arguments; QuizResultScreen(navController, a?.getInt("score")?:0, a?.getInt("total")?:0, a?.getInt("xpEarned")?:0)
        }
        composable(Screen.Profile.route) { ProfileScreen(navController) }
        composable(Screen.StudyTimer.route) { StudyTimerScreen(navController) }
        composable(Screen.FocusMode.route) { FocusModeScreen(navController) }
        composable(Screen.WeakTopics.route) { WeakTopicsScreen(navController) }
        composable(Screen.DailyQuests.route) { DailyQuestScreen(navController) }
    }
}
