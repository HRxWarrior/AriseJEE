package com.arisejee.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.arisejee.app.ui.components.GlowCard
import com.arisejee.app.ui.components.QuestCard
import com.arisejee.app.ui.components.RankBadge
import com.arisejee.app.ui.components.SystemPanel
import com.arisejee.app.ui.components.XpBar
import com.arisejee.app.ui.navigation.Screen
import com.arisejee.app.ui.theme.DarkBg
import com.arisejee.app.ui.theme.NeonBlue
import com.arisejee.app.ui.theme.NeonCyan
import com.arisejee.app.ui.theme.NeonGold
import com.arisejee.app.ui.theme.NeonPurple
import com.arisejee.app.ui.theme.NeonRed
import com.arisejee.app.ui.theme.TextPrimary
import com.arisejee.app.ui.theme.TextSecondary
import com.arisejee.app.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(navController: NavController, vm: DashboardViewModel = viewModel()) {
    val user by vm.user.collectAsState()
    val quests by vm.quests.collectAsState()
    val weakTopics by vm.weakTopics.collectAsState()

    LaunchedEffect(Unit) { vm.updateStreak() }

    Column(Modifier.fillMaxSize().background(DarkBg).verticalScroll(rememberScrollState()).padding(16.dp)) {
        Text("SYSTEM", color = NeonCyan, fontWeight = FontWeight.Bold, fontSize = 12.sp, letterSpacing = 4.sp)
        Text("Arise, Hunter", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Spacer(Modifier.height(16.dp))

        user?.let { u ->
            GlowCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text(u.username, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("Level ${u.level}", color = NeonPurple, fontSize = 14.sp)
                        Spacer(Modifier.height(4.dp))
                        XpBar(u.xp)
                    }
                    Spacer(Modifier.width(12.dp))
                    RankBadge(u.rank)
                }
            }
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                StatItem("Streak", "${u.streak} days", NeonGold)
                StatItem("Solved", "${u.totalQuestionsSolved}", NeonBlue)
                val acc = if (u.totalQuestionsSolved > 0) (u.correctAnswers * 100 / u.totalQuestionsSolved) else 0
                StatItem("Accuracy", "$acc%", if (acc > 70) NeonCyan else NeonRed)
            }
        }

        Spacer(Modifier.height(20.dp))
        SystemPanel("DAILY QUESTS") {
            if (quests.isEmpty()) Text("Loading quests...", color = TextSecondary)
            quests.forEach { q ->
                QuestCard(q.title, q.description, q.currentCount, q.targetCount, q.xpReward, q.isCompleted)
                Spacer(Modifier.height(8.dp))
            }
            Spacer(Modifier.height(4.dp))
            Text("View All Quests >", color = NeonBlue, modifier = Modifier.clickable { navController.navigate(Screen.DailyQuests.route) })
        }

        Spacer(Modifier.height(16.dp))
        if (weakTopics.isNotEmpty()) {
            SystemPanel("WEAK AREAS DETECTED", glowColor = NeonRed) {
                weakTopics.take(3).forEach { wt ->
                    Row(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Text(wt.chapter, color = TextPrimary, modifier = Modifier.weight(1f), fontSize = 13.sp)
                        Text(wt.severity, color = if (wt.severity == "HIGH") NeonRed else NeonGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(Modifier.height(4.dp))
                Text("See Full Analysis >", color = NeonRed, modifier = Modifier.clickable { navController.navigate(Screen.WeakTopics.route) })
            }
        }
        Spacer(Modifier.height(80.dp))
    }
}

@Composable
private fun StatItem(label: String, value: String, color: androidx.compose.ui.graphics.Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, color = color, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(label, color = TextSecondary, fontSize = 11.sp)
    }
}
