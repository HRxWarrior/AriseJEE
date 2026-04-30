package com.arisejee.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.arisejee.app.ui.theme.NeonBlue
import com.arisejee.app.ui.theme.NeonCyan
import com.arisejee.app.ui.theme.NeonGreen
import com.arisejee.app.ui.theme.NeonPurple
import com.arisejee.app.ui.theme.TextPrimary
import com.arisejee.app.ui.theme.TextSecondary

@Composable
fun SubjectListScreen(navController: NavController) {
    Column(Modifier.fillMaxSize().background(DarkBg).padding(16.dp)) {
        Text("SYSTEM", color = NeonCyan, fontWeight = FontWeight.Bold, fontSize = 12.sp, letterSpacing = 4.sp)
        Text("Select Subject", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Spacer(Modifier.height(20.dp))
        val subjects = SubjectData.all
        val colors = listOf(NeonBlue, NeonPurple, NeonGreen)
        val icons = listOf(Icons.Default.Speed, Icons.Default.Science, Icons.Default.Calculate)
        subjects.forEachIndexed { i, sub ->
            GlowCard(
                glowColor = colors[i],
                modifier = Modifier.clickable {
                    navController.navigate(Screen.Chapters.createRoute(sub.name))
                }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(icons[i], sub.name, tint = colors[i], modifier = Modifier.size(32.dp))
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text(sub.name, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("${sub.chapters.size} chapters", color = TextSecondary, fontSize = 13.sp)
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
        }
    }
}
