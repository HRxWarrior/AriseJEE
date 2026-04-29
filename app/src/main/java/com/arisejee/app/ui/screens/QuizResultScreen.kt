package com.arisejee.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.arisejee.app.ui.components.GlowButton
import com.arisejee.app.ui.components.SystemPanel
import com.arisejee.app.ui.theme.DarkBg
import com.arisejee.app.ui.theme.NeonBlue
import com.arisejee.app.ui.theme.NeonCyan
import com.arisejee.app.ui.theme.NeonGold
import com.arisejee.app.ui.theme.NeonGreen
import com.arisejee.app.ui.theme.NeonRed
import com.arisejee.app.ui.theme.TextPrimary
import com.arisejee.app.ui.theme.TextSecondary

@Composable
fun QuizResultScreen(navController: NavController, score: Int, total: Int, xpEarned: Int) {
    val pct = if (total > 0) score * 100 / total else 0
    Column(Modifier.fillMaxSize().background(DarkBg).padding(24.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("QUEST COMPLETE", color = NeonCyan, fontWeight = FontWeight.Bold, fontSize = 14.sp, letterSpacing = 4.sp)
        Spacer(Modifier.height(16.dp))
        Text("$score / $total", color = if (pct >= 70) NeonGreen else if (pct >= 40) NeonGold else NeonRed, fontWeight = FontWeight.Bold, fontSize = 48.sp)
        Text("$pct% accuracy", color = TextSecondary, fontSize = 16.sp)
        Spacer(Modifier.height(24.dp))
        SystemPanel("REWARDS") {
            Text("+$xpEarned XP earned", color = NeonGold, fontWeight = FontWeight.Bold, fontSize = 18.sp, textAlign = TextAlign.Center)
        }
        Spacer(Modifier.height(32.dp))
        GlowButton("Return to Dashboard", onClick = { navController.navigate("dashboard") { popUpTo("dashboard") { inclusive = true } } })
    }
}
