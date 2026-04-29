package com.arisejee.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arisejee.app.ui.theme.DarkCard
import com.arisejee.app.ui.theme.NeonBlue
import com.arisejee.app.ui.theme.NeonGold
import com.arisejee.app.ui.theme.NeonGreen
import com.arisejee.app.ui.theme.TextPrimary
import com.arisejee.app.ui.theme.TextSecondary

@Composable
fun QuestCard(title: String, description: String, current: Int, target: Int, xpReward: Int, isCompleted: Boolean) {
    val progress = if (target > 0) current.toFloat() / target else 0f
    val glow = if (isCompleted) NeonGreen else NeonBlue
    val shape = RoundedCornerShape(12.dp)
    Column(Modifier.fillMaxWidth().border(1.dp, glow.copy(alpha=0.4f), shape).background(DarkCard, shape).padding(12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(title, color=TextPrimary, fontWeight=FontWeight.Bold, fontSize=14.sp, modifier=Modifier.weight(1f))
            Text("+$xpReward XP", color=NeonGold, fontSize=12.sp, fontWeight=FontWeight.Bold)
        }
        Text(description, color=TextSecondary, fontSize=12.sp)
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            @Suppress("DEPRECATION")
            LinearProgressIndicator(progress = progress.coerceIn(0f,1f), modifier=Modifier.weight(1f).height(6.dp).clip(RoundedCornerShape(3.dp)), color=glow, trackColor=DarkCard)
            Spacer(Modifier.width(8.dp))
            Text("$current/$target", color=TextSecondary, fontSize=11.sp)
        }
    }
}
