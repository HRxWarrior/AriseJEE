package com.arisejee.app.ui.theme

import androidx.compose.ui.graphics.Color

val DarkBg = Color(0xFF0A0A1A)
val DarkSurface = Color(0xFF12122A)
val DarkCard = Color(0xFF1A1A3E)
val NeonBlue = Color(0xFF4FC3F7)
val NeonPurple = Color(0xFFB388FF)
val NeonCyan = Color(0xFF00E5FF)
val NeonGreen = Color(0xFF69F0AE)
val NeonRed = Color(0xFFFF5252)
val NeonGold = Color(0xFFFFD740)
val TextPrimary = Color(0xFFE0E0E0)
val TextSecondary = Color(0xFF9E9E9E)
val TextDim = Color(0xFF616161)

val RankE = Color(0xFF9E9E9E)
val RankD = Color(0xFF4CAF50)
val RankC = Color(0xFF2196F3)
val RankB = Color(0xFF9C27B0)
val RankA = Color(0xFFFFD700)
val RankS = Color(0xFFF44336)
val RankNational = Color(0xFFFF6D00)

fun rankColor(rank: String): Color = when (rank) {
    "E" -> RankE; "D" -> RankD; "C" -> RankC; "B" -> RankB
    "A" -> RankA; "S" -> RankS; "NATIONAL" -> RankNational; else -> RankE
}
