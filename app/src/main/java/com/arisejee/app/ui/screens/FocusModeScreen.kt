package com.arisejee.app.ui.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.arisejee.app.ui.components.GlowButton
import com.arisejee.app.ui.theme.DarkBg
import com.arisejee.app.ui.theme.NeonCyan
import com.arisejee.app.ui.theme.NeonGold
import com.arisejee.app.ui.theme.NeonRed
import com.arisejee.app.ui.theme.TextPrimary
import com.arisejee.app.ui.theme.TextSecondary
import com.arisejee.app.util.TimeFormatter

@Composable
fun FocusModeScreen(navController: NavController) {
    var showExitWarning by remember { mutableStateOf(false) }
    var showLeaveWarning by remember { mutableStateOf(false) }
    var focusSeconds by remember { mutableLongStateOf(0L) }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE) showLeaveWarning = true
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    // Timer
    androidx.compose.runtime.LaunchedEffect(Unit) {
        while (true) { kotlinx.coroutines.delay(1000L); focusSeconds++ }
    }

    BackHandler { showExitWarning = true }

    Box(Modifier.fillMaxSize().background(DarkBg), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.padding(32.dp)) {
            Text("FOCUS MODE", color = NeonCyan, fontWeight = FontWeight.Bold, fontSize = 16.sp, letterSpacing = 6.sp)
            Spacer(Modifier.height(8.dp))
            Text("Stay focused, Hunter", color = TextSecondary, fontSize = 14.sp)
            Spacer(Modifier.height(40.dp))
            Text(TimeFormatter.secondsToTimer(focusSeconds), color = NeonGold, fontWeight = FontWeight.Bold, fontSize = 64.sp)
            Spacer(Modifier.height(16.dp))
            Text("Do not leave this screen.\nYour discipline defines your rank.", color = TextSecondary, fontSize = 13.sp, textAlign = TextAlign.Center, lineHeight = 20.sp)
            Spacer(Modifier.height(48.dp))
            GlowButton("Exit Focus Mode", onClick = { showExitWarning = true })
        }
    }

    if (showExitWarning) {
        AlertDialog(
            onDismissRequest = { showExitWarning = false },
            title = { Text("Leave Focus Mode?", color = TextPrimary) },
            text = { Text("Exiting will end your focus session. Are you sure?", color = TextSecondary) },
            confirmButton = { TextButton(onClick = { navController.popBackStack() }) { Text("Leave", color = NeonRed) } },
            dismissButton = { TextButton(onClick = { showExitWarning = false }) { Text("Stay", color = NeonCyan) } },
            containerColor = com.arisejee.app.ui.theme.DarkCard
        )
    }

    if (showLeaveWarning) {
        AlertDialog(
            onDismissRequest = { showLeaveWarning = false },
            title = { Text("You left the app!", color = NeonRed) },
            text = { Text("A true hunter stays focused. Return to your training.", color = TextSecondary) },
            confirmButton = { TextButton(onClick = { showLeaveWarning = false }) { Text("OK", color = NeonCyan) } },
            containerColor = com.arisejee.app.ui.theme.DarkCard
        )
    }
}
