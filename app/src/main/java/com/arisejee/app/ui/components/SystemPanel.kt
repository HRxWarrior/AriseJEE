package com.arisejee.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arisejee.app.ui.theme.DarkCard
import com.arisejee.app.ui.theme.NeonCyan

@Composable
fun SystemPanel(title: String, glowColor: Color = NeonCyan, content: @Composable () -> Unit) {
    val shape = RoundedCornerShape(12.dp)
    Column(Modifier.fillMaxWidth().border(1.dp, glowColor.copy(alpha=0.4f), shape).background(DarkCard.copy(alpha=0.8f), shape).padding(16.dp)) {
        Text("[ $title ]", color=glowColor, fontWeight=FontWeight.Bold, fontSize=14.sp, letterSpacing=2.sp)
        Spacer(Modifier.height(12.dp))
        content()
    }
}
