package com.arisejee.app.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.arisejee.app.ui.components.GlowButton
import com.arisejee.app.ui.components.GlowCard
import com.arisejee.app.ui.components.RankBadge
import com.arisejee.app.ui.components.SystemPanel
import com.arisejee.app.ui.components.XpBar
import com.arisejee.app.ui.theme.DarkBg
import com.arisejee.app.ui.theme.DarkCard
import com.arisejee.app.ui.theme.NeonBlue
import com.arisejee.app.ui.theme.NeonCyan
import com.arisejee.app.ui.theme.NeonGold
import com.arisejee.app.ui.theme.NeonPurple
import com.arisejee.app.ui.theme.TextPrimary
import com.arisejee.app.ui.theme.TextSecondary
import com.arisejee.app.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(navController: NavController, vm: ProfileViewModel = viewModel()) {
    val user by vm.user.collectAsState()
    var showNameDialog by remember { mutableStateOf(false) }
    var nameInput by remember { mutableStateOf("") }

    val picPicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { vm.updateProfilePic(it.toString()) }
    }
    val ttPicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { vm.updateTimetable(it.toString()) }
    }

    Column(Modifier.fillMaxSize().background(DarkBg).verticalScroll(rememberScrollState()).padding(16.dp)) {
        Text("SYSTEM", color = NeonCyan, fontWeight = FontWeight.Bold, fontSize = 12.sp, letterSpacing = 4.sp)
        Text("Hunter Profile", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Spacer(Modifier.height(20.dp))

        user?.let { u ->
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Box(contentAlignment = Alignment.BottomEnd) {
                    if (u.profilePicUri != null) {
                        AsyncImage(model = u.profilePicUri, contentDescription = "Profile", modifier = Modifier.size(100.dp).clip(CircleShape).border(2.dp, NeonPurple, CircleShape), contentScale = ContentScale.Crop)
                    } else {
                        Box(Modifier.size(100.dp).clip(CircleShape).background(DarkCard).border(2.dp, NeonPurple, CircleShape), contentAlignment = Alignment.Center) {
                            Text(u.username.take(2).uppercase(), color = NeonPurple, fontWeight = FontWeight.Bold, fontSize = 28.sp)
                        }
                    }
                    IconButton(onClick = { picPicker.launch("image/*") }, modifier = Modifier.size(32.dp).clip(CircleShape).background(NeonBlue)) {
                        Icon(Icons.Default.CameraAlt, "Change photo", tint = DarkBg, modifier = Modifier.size(16.dp))
                    }
                }
                Spacer(Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(u.username, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    IconButton(onClick = { nameInput = u.username; showNameDialog = true }) { Icon(Icons.Default.Edit, "Edit", tint = NeonBlue, modifier = Modifier.size(18.dp)) }
                }
                RankBadge(u.rank)
                Spacer(Modifier.height(8.dp))
                Text("Level ${u.level}", color = NeonPurple, fontSize = 14.sp)
                XpBar(u.xp, Modifier.padding(horizontal = 32.dp))
            }

            Spacer(Modifier.height(20.dp))
            SystemPanel("STATS") {
                StatRow("Total XP", "${u.xp}", NeonGold)
                StatRow("Questions Solved", "${u.totalQuestionsSolved}", NeonBlue)
                StatRow("Correct Answers", "${u.correctAnswers}", NeonCyan)
                val acc = if (u.totalQuestionsSolved > 0) "${u.correctAnswers * 100 / u.totalQuestionsSolved}%" else "N/A"
                StatRow("Accuracy", acc, NeonPurple)
                StatRow("Current Streak", "${u.streak} days", NeonGold)
            }

            Spacer(Modifier.height(16.dp))
            SystemPanel("TIMETABLE") {
                if (u.timetableUri != null) {
                    Text("Timetable attached", color = NeonCyan, fontSize = 13.sp)
                    Spacer(Modifier.height(8.dp))
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    GlowButton("Upload Timetable", onClick = { ttPicker.launch("*/*") })
                    if (u.timetableUri != null) GlowButton("Remove", onClick = { vm.updateTimetable(null) })
                }
            }
        }
        Spacer(Modifier.height(80.dp))
    }

    if (showNameDialog) {
        AlertDialog(
            onDismissRequest = { showNameDialog = false },
            title = { Text("Set Username", color = TextPrimary) },
            text = {
                OutlinedTextField(value = nameInput, onValueChange = { nameInput = it }, singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = NeonBlue, unfocusedBorderColor = TextSecondary, focusedTextColor = TextPrimary, unfocusedTextColor = TextPrimary))
            },
            confirmButton = { TextButton(onClick = { vm.updateUsername(nameInput); showNameDialog = false }) { Text("Save", color = NeonBlue) } },
            dismissButton = { TextButton(onClick = { showNameDialog = false }) { Text("Cancel", color = TextSecondary) } },
            containerColor = DarkCard, shape = RoundedCornerShape(16.dp)
        )
    }
}

@Composable
private fun StatRow(label: String, value: String, color: androidx.compose.ui.graphics.Color) {
    Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = TextSecondary, fontSize = 13.sp)
        Text(value, color = color, fontWeight = FontWeight.Bold, fontSize = 13.sp)
    }
}
