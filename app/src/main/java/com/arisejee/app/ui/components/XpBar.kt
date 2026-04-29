package com.arisejee.app.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arisejee.app.ui.theme.DarkCard
import com.arisejee.app.ui.theme.NeonBlue
import com.arisejee.app.ui.theme.NeonPurple

@Composable
fun XpBar(currentXp: Int, modifier: Modifier = Modifier) {
    val progress = (currentXp % 100).toFloat() / 100f
    val anim by animateFloatAsState(targetValue = progress, animationSpec = tween(600), label = "xp")
    Column(modifier = modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("XP", color = NeonBlue, fontSize = 12.sp)
            Spacer(Modifier.width(8.dp))
            Text("$currentXp", color = NeonPurple, fontSize = 12.sp)
        }
        Spacer(Modifier.height(4.dp))
        Box(Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)).background(DarkCard)) {
            Box(Modifier.fillMaxWidth(anim.coerceIn(0f,1f)).fillMaxHeight().clip(RoundedCornerShape(4.dp)).background(Brush.horizontalGradient(listOf(NeonBlue, NeonPurple))))
        }
    }
}
