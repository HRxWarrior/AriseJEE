package com.arisejee.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arisejee.app.data.model.Rank
import com.arisejee.app.ui.theme.DarkCard
import com.arisejee.app.ui.theme.rankColor

@Composable
fun RankBadge(rank: String, modifier: Modifier = Modifier) {
    val color = rankColor(rank)
    Box(modifier = modifier.border(2.dp, color, RoundedCornerShape(8.dp)).background(DarkCard, RoundedCornerShape(8.dp)).padding(horizontal=12.dp, vertical=6.dp), contentAlignment = Alignment.Center) {
        Text(Rank.fromString(rank).title, color=color, fontWeight=FontWeight.Bold, fontSize=14.sp)
    }
}
