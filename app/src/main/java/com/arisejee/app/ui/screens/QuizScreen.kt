package com.arisejee.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.arisejee.app.ui.components.GlowButton
import com.arisejee.app.ui.components.SystemPanel
import com.arisejee.app.ui.navigation.Screen
import com.arisejee.app.ui.theme.DarkBg
import com.arisejee.app.ui.theme.DarkCard
import com.arisejee.app.ui.theme.NeonBlue
import com.arisejee.app.ui.theme.NeonCyan
import com.arisejee.app.ui.theme.NeonGreen
import com.arisejee.app.ui.theme.NeonGold
import com.arisejee.app.ui.theme.NeonRed
import com.arisejee.app.ui.theme.TextPrimary
import com.arisejee.app.ui.theme.TextSecondary
import com.arisejee.app.util.TimeFormatter
import com.arisejee.app.viewmodel.QuizViewModel

@Composable
fun QuizScreen(navController: NavController, subject: String, chapter: String, examType: String, vm: QuizViewModel = viewModel()) {
    val questions by vm.questions.collectAsState()
    val idx by vm.currentIndex.collectAsState()
    val selected by vm.selectedAnswer.collectAsState()
    val showResult by vm.showResult.collectAsState()
    val score by vm.score.collectAsState()
    val totalXp by vm.totalXp.collectAsState()
    val finished by vm.quizFinished.collectAsState()
    val timer by vm.timer.collectAsState()
    val loading by vm.isLoading.collectAsState()
    val loadMoreMsg by vm.loadMoreMessage.collectAsState()

    LaunchedEffect(subject, chapter, examType) { vm.loadQuiz(subject, chapter, examType) }
    LaunchedEffect(finished) {
        if (finished) navController.navigate(Screen.QuizResult.createRoute(score, questions.size, totalXp)) {
            popUpTo(Screen.Quiz.route) { inclusive = true }
        }
    }

    Box(Modifier.fillMaxSize().background(DarkBg)) {
        Column(Modifier.fillMaxSize().padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, "Back", tint = NeonBlue) }
                Column(Modifier.weight(1f)) {
                    Text(chapter, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("${idx + 1}/${questions.size}", color = TextSecondary, fontSize = 12.sp)
                }
                Text(TimeFormatter.secondsToTimer(timer), color = NeonCyan, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Spacer(Modifier.height(12.dp))

            if (loading || questions.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator(color = NeonBlue) }
            } else {
                val q = questions[idx]
                Column(Modifier.verticalScroll(rememberScrollState()).weight(1f)) {
                    SystemPanel("QUESTION ${idx + 1}") {
                        Text(q.difficulty, color = when(q.difficulty) { "EASY" -> NeonGreen; "HARD" -> NeonRed; else -> NeonBlue }, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Text(q.questionText, color = TextPrimary, fontSize = 15.sp, lineHeight = 22.sp)
                    }
                    Spacer(Modifier.height(16.dp))
                    val options = listOf(q.optionA, q.optionB, q.optionC, q.optionD)
                    options.forEachIndexed { i, opt ->
                        val isSelected = selected == i
                        val isCorrect = showResult && i == q.correctAnswer
                        val isWrong = showResult && isSelected && i != q.correctAnswer
                        val borderColor = when {
                            isCorrect -> NeonGreen; isWrong -> NeonRed
                            isSelected -> NeonBlue; else -> TextSecondary.copy(alpha = 0.3f)
                        }
                        val bgColor = when {
                            isCorrect -> NeonGreen.copy(alpha = 0.1f); isWrong -> NeonRed.copy(alpha = 0.1f)
                            isSelected -> NeonBlue.copy(alpha = 0.1f); else -> DarkCard
                        }
                        Box(
                            Modifier.fillMaxWidth().padding(vertical = 4.dp)
                                .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                                .background(bgColor, RoundedCornerShape(12.dp))
                                .clickable(enabled = !showResult) { vm.selectAnswer(i) }
                                .padding(14.dp)
                        ) {
                            Row {
                                Text("${('A' + i)}. ", color = borderColor, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text(opt, color = TextPrimary, fontSize = 14.sp)
                            }
                        }
                    }
                    if (showResult && selected != q.correctAnswer) {
                        Spacer(Modifier.height(12.dp))
                        SystemPanel("SOLUTION", glowColor = NeonGreen) {
                            Text(q.solution, color = TextPrimary, fontSize = 13.sp, lineHeight = 20.sp)
                        }
                    }
                }
                Spacer(Modifier.height(12.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    if (!showResult) {
                        GlowButton("Confirm", onClick = { if (selected >= 0) vm.confirmAnswer() }, enabled = selected >= 0)
                    } else {
                        GlowButton("Next", onClick = { vm.nextQuestion() })
                    }
                    Spacer(Modifier.height(8.dp))
                    GlowButton("Load More Questions", onClick = { vm.loadMore(subject, chapter, examType) })
                }
            }
        }

        // Snackbar for Load More feedback
        if (loadMoreMsg.isNotEmpty()) {
            LaunchedEffect(loadMoreMsg) {
                kotlinx.coroutines.delay(3000)
                vm.clearLoadMoreMessage()
            }
            Snackbar(
                modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp),
                containerColor = DarkCard,
                contentColor = NeonCyan
            ) {
                Text(loadMoreMsg, color = NeonCyan, fontSize = 13.sp)
            }
        }
    }
}
