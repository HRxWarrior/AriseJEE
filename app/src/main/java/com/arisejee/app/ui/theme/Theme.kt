package com.arisejee.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkScheme = darkColorScheme(
    primary=NeonBlue, secondary=NeonPurple, tertiary=NeonCyan,
    background=DarkBg, surface=DarkSurface,
    onPrimary=DarkBg, onSecondary=DarkBg, onTertiary=DarkBg,
    onBackground=TextPrimary, onSurface=TextPrimary, error=NeonRed, onError=DarkBg,
)

@Composable
fun AriseJEETheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme=DarkScheme, typography=AriseTypography, content=content)
}
