package com.youssef.waterapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.youssef.waterapp.WatterBottle
import com.youssef.waterapp.ui.theme.WaterAppTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaterAppTheme {
                var showSplash by remember { mutableStateOf(true) }
                var totalWaterAmount by remember { mutableStateOf(2500) }
                var usedWaterAmount by remember { mutableStateOf(100) }

                if (showSplash) {
                    SplashScreen {
                        showSplash = false
                    }
                } else {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        val navController = rememberNavController()

                        Column(modifier = Modifier.fillMaxSize()) {
                            NavHost(
                                navController = navController,
                                startDestination = Screen.WaterBottle.route,
                                modifier = Modifier.weight(1f)
                            ) {
                                composable(Screen.WaterBottle.route) {
                                    HomeScreen(
                                        totalWaterAmount = totalWaterAmount,
                                        usedWaterAmount = usedWaterAmount,
                                        onDrink = {
                                            usedWaterAmount = (usedWaterAmount + 200)
                                                .coerceAtMost(totalWaterAmount)
                                        }
                                    )
                                }
                                composable(Screen.Settings.route) {
                                    SettingsScreen(
                                        currentTotalWaterAmount = totalWaterAmount,
                                        onTotalWaterAmountUpdated = { totalWaterAmount = it },
                                        onResetBottle = { usedWaterAmount = 0 }
                                    )
                                }
                            }
                            BottomNavigationBar(navController = navController)
                        }
                    }
                }
            }
        }
    }
}