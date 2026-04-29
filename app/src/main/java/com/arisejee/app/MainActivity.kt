package com.arisejee.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.arisejee.app.ui.components.BottomNav
import com.arisejee.app.ui.components.bottomNavItems
import com.arisejee.app.ui.navigation.NavGraph
import com.arisejee.app.ui.theme.AriseJEETheme
import com.arisejee.app.ui.theme.DarkBg

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AriseJEETheme {
                val navController = rememberNavController()
                val currentRoute by navController.currentBackStackEntryAsState()
                val showBottom = currentRoute?.destination?.route in bottomNavItems.map { it.route }
                Scaffold(bottomBar = { if (showBottom) BottomNav(navController) }, containerColor = DarkBg) { padding ->
                    Box(modifier = Modifier.padding(padding)) { NavGraph(navController) }
                }
            }
        }
    }
}
