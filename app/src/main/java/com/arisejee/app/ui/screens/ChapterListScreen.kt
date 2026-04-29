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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.arisejee.app.data.model.SubjectData
import com.arisejee.app.ui.components.GlowCard
import com.arisejee.app.ui.navigation.Screen
import com.arisejee.app.ui.theme.DarkBg
import com.arisejee.app.ui.theme.DarkCard
import com.arisejee.app.ui.theme.NeonBlue
import com.arisejee.app.ui.theme.NeonCyan
import com.arisejee.app.ui.theme.NeonPurple
import com.arisejee.app.ui.theme.TextPrimary
import com.arisejee.app.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterListScreen(navController: NavController, subject: String) {
    val chapters = SubjectData.all.find { it.name == subject }?.chapters ?: emptyList()
    var examType by remember { mutableStateOf("JEE_MAINS") }

    Column(Modifier.fillMaxSize().background(DarkBg).padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Default.ArrowBack, "Back", tint = NeonBlue) }
            Column {
                Text("SYSTEM", color = NeonCyan, fontWeight = FontWeight.Bold, fontSize = 10.sp, letterSpacing = 4.sp)
                Text(subject, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
        }
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("JEE_MAINS" to "JEE Mains", "JEE_ADVANCED" to "JEE Advanced").forEach { (key, label) ->
                FilterChip(
                    selected = examType == key, onClick = { examType = key },
                    label = { Text(label, fontSize = 12.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = NeonPurple.copy(alpha = 0.2f),
                        selectedLabelColor = NeonPurple,
                        containerColor = DarkCard, labelColor = TextSecondary
                    ), shape = RoundedCornerShape(20.dp)
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        LazyColumn {
            itemsIndexed(chapters) { idx, ch ->
                GlowCard(modifier = Modifier.padding(vertical = 4.dp).clickable {
                    navController.navigate(Screen.Quiz.createRoute(subject, ch.name, examType))
                }) {
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text("${idx + 1}", color = NeonBlue, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(Modifier.padding(horizontal = 8.dp))
                        Column { Text(ch.name, color = TextPrimary, fontWeight = FontWeight.Medium, fontSize = 15.sp) }
                    }
                }
            }
        }
    }
}
