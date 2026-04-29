package com.arisejee.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.arisejee.app.ui.theme.DarkSurface
import com.arisejee.app.ui.theme.NeonBlue
import com.arisejee.app.ui.theme.TextDim

data class BottomNavItem(val route: String, val label: String, val icon: ImageVector)
val bottomNavItems = listOf(
    BottomNavItem("dashboard", "Dashboard", Icons.Default.Dashboard),
    BottomNavItem("subjects", "Subjects", Icons.Default.MenuBook),
    BottomNavItem("study_timer", "Timer", Icons.Default.Timer),
    BottomNavItem("profile", "Profile", Icons.Default.Person),
)

@Composable
fun BottomNav(navController: NavController) {
    val current = navController.currentBackStackEntryAsState().value?.destination?.route
    NavigationBar(containerColor = DarkSurface) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = current == item.route,
                onClick = { if (current != item.route) navController.navigate(item.route) { popUpTo("dashboard") { saveState = true }; launchSingleTop = true; restoreState = true } },
                icon = { Icon(item.icon, item.label) },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(selectedIconColor=NeonBlue, selectedTextColor=NeonBlue, unselectedIconColor=TextDim, unselectedTextColor=TextDim, indicatorColor=DarkSurface)
            )
        }
    }
}
