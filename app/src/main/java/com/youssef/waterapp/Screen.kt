package com.youssef.waterapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector? = null
) {
    object WaterBottle : Screen(
        route = "water_bottle",
        title = "Water Tracker",
        icon = Icons.Default.Home  // More appropriate icon
    )

    object Settings : Screen(
        route = "settings",
        title = "Profile",
        icon = Icons.Default.Person

    )

    companion object {
        fun getScreens() = listOf(WaterBottle, Settings)
    }
}