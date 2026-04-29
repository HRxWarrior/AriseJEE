package com.arisejee.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.arisejee.app.ui.components.GlowCard
import com.arisejee.app.ui.components.SystemPanel
import com.arisejee.app.ui.navigation.Screen
import com.arisejee.app.ui.theme.DarkBg
import com.arisejee.app.ui.theme.NeonBlue
import com.arisejee.app.ui.theme.NeonCyan
import com.arisejee.app.ui.theme.NeonGold
import com.arisejee.app.ui.theme.NeonGreen
import com.arisejee.app.ui.theme.NeonPurple
import com.arisejee.app.ui.theme.NeonRed
import com.arisejee.app.ui.theme.TextPrimary
import com.arisejee.app.ui.theme.TextSecondary
import com.arisejee.app.util.TimeFormatter
import com.arisejee.app.viewmodel.StudyTimerViewModel

@Composable
fun StudyTimerScreen(navController: NavController, vm: StudyTimerViewModel = viewModel()) {
    val elapsed by vm.elapsedSeconds.collectAsState()
    val running by vm.isRunning.collectAsState()
    val todayMin by vm.todayMinutes.collectAsState()
    val weekMin by vm.weekMinutes.collectAsState()
    val totalMin by vm.totalMinutes.collectAsState()

    Column(Modifier.fillMaxSize().background(DarkBg).padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("SYSTEM", color = NeonCyan, fontWeight = FontWeight.Bold, fontSize = 12.sp, letterSpacing = 4.sp)
        Text("Study Timer", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Spacer(Modifier.height(40.dp))

        Text(TimeFormatter.secondsToTimer(elapsed), color = if (running) NeonGreen else NeonBlue, fontWeight = FontWeight.Bold, fontSize = 56.sp)
        Text(if (running) "Session in progress..." else "Ready to train", color = TextSecondary, fontSize = 14.sp)
        Spacer(Modifier.height(32.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            if (!running) {
                GlowButton("Start Session", onClick = { vm.startTimer() })
            } else {
                GlowButton("Stop", onClick = { vm.stopTimer() })
                GlowButton("Focus Mode", onClick = { navController.navigate(Screen.FocusMode.route) })
            }
        }

        Spacer(Modifier.height(40.dp))
        SystemPanel("STUDY LOG") {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                TimeStatColumn("Today", TimeFormatter.minutesToDisplay(todayMin), NeonGreen)
                TimeStatColumn("This Week", TimeFormatter.minutesToDisplay(weekMin), NeonBlue)
                TimeStatColumn("All Time", TimeFormatter.minutesToDisplay(totalMin), NeonPurple)
            }
        }
        Spacer(Modifier.height(80.dp))
    }
}

@Composable
private fun TimeStatColumn(label: String, value: String, color: androidx.compose.ui.graphics.Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, color = color, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(label, color = TextSecondary, fontSize = 11.sp)
    }
}
