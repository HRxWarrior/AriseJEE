package com.arisejee.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arisejee.app.ui.theme.DarkCard
import com.arisejee.app.ui.theme.NeonBlue

@Composable
fun GlowCard(modifier: Modifier = Modifier, glowColor: Color = NeonBlue, borderWidth: Dp = 1.dp, content: @Composable () -> Unit) {
    val shape = RoundedCornerShape(16.dp)
    Box(modifier = modifier.fillMaxWidth().clip(shape).border(borderWidth, glowColor.copy(alpha=0.5f), shape).background(DarkCard, shape).padding(16.dp)) { content() }
}
