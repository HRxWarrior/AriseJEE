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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.arisejee.app.ui.components.GlowCard
import com.arisejee.app.ui.theme.DarkBg
import com.arisejee.app.ui.theme.NeonBlue
import com.arisejee.app.ui.theme.NeonCyan
import com.arisejee.app.ui.theme.NeonGold
import com.arisejee.app.ui.theme.NeonRed
import com.arisejee.app.ui.theme.TextPrimary
import com.arisejee.app.ui.theme.TextSecondary
import com.arisejee.app.viewmodel.WeakTopicViewModel

@Composable
fun WeakTopicsScreen(navController: NavController, vm: WeakTopicViewModel = viewModel()) {
    val topics by vm.weakTopics.collectAsState()

    Column(Modifier.fillMaxSize().background(DarkBg).padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, "Back", tint = NeonBlue) }
            Column {
                Text("SYSTEM", color = NeonCyan, fontWeight = FontWeight.Bold, fontSize = 10.sp, letterSpacing = 4.sp)
                Text("Weak Areas Analysis", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
        }
        Spacer(Modifier.height(16.dp))

        if (topics.isEmpty()) {
            Text("No weak areas detected yet. Solve more questions to get analysis.", color = TextSecondary, fontSize = 14.sp, modifier = Modifier.padding(16.dp))
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(topics) { wt ->
                    val severityColor = when (wt.severity) { "HIGH" -> NeonRed; "MEDIUM" -> NeonGold; else -> NeonBlue }
                    GlowCard(glowColor = severityColor) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column(Modifier.weight(1f)) {
                                Text(wt.chapter, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Text(wt.subject, color = TextSecondary, fontSize = 12.sp)
                            }
                            Text(wt.severity, color = severityColor, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                        Spacer(Modifier.height(8.dp))
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Wrong: ${wt.wrongCount}/${wt.totalAttempts}", color = TextSecondary, fontSize = 12.sp)
                            Text("Avg time: ${"%.0f".format(wt.avgTimeSeconds)}s", color = TextSecondary, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}
