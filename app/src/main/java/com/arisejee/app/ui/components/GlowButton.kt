package com.arisejee.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arisejee.app.ui.theme.DarkCard
import com.arisejee.app.ui.theme.NeonBlue
import com.arisejee.app.ui.theme.NeonPurple

@Composable
fun GlowButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier, enabled: Boolean = true) {
    val shape = RoundedCornerShape(12.dp)
    val brush = if (enabled) Brush.horizontalGradient(listOf(NeonBlue, NeonPurple)) else Brush.horizontalGradient(listOf(Color.Gray, Color.Gray))
    Box(modifier = modifier.border(2.dp, brush, shape).background(if (enabled) DarkCard else DarkCard.copy(alpha=0.5f), shape).clickable(enabled=enabled) { onClick() }.padding(horizontal=24.dp, vertical=12.dp), contentAlignment = Alignment.Center) {
        Text(text, color = if (enabled) NeonBlue else Color.Gray, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}
