package com.arisejee.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.arisejee.app.ui.components.QuestCard
import com.arisejee.app.ui.theme.DarkBg
import com.arisejee.app.ui.theme.NeonBlue
import com.arisejee.app.ui.theme.NeonCyan
import com.arisejee.app.ui.theme.TextPrimary
import com.arisejee.app.ui.theme.TextSecondary
import com.arisejee.app.viewmodel.DashboardViewModel

@Composable
fun DailyQuestScreen(navController: NavController, vm: DashboardViewModel = viewModel()) {
    val quests by vm.quests.collectAsState()

    Column(Modifier.fillMaxSize().background(DarkBg).padding(16.dp)) {
        androidx.compose.foundation.layout.Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, "Back", tint = NeonBlue) }
            Column {
                Text("SYSTEM", color = NeonCyan, fontWeight = FontWeight.Bold, fontSize = 10.sp, letterSpacing = 4.sp)
                Text("Daily Quests", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
        }
        Spacer(Modifier.height(16.dp))
        if (quests.isEmpty()) {
            Text("Loading daily quests...", color = TextSecondary, fontSize = 14.sp)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(quests) { q ->
                    QuestCard(q.title, q.description, q.currentCount, q.targetCount, q.xpReward, q.isCompleted)
                }
            }
        }
    }
}
