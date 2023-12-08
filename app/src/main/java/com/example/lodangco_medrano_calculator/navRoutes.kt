package com.example.lodangco_medrano_calculator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.ui.graphics.vector.ImageVector



sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
){
    object  Calculator: BottomBarScreen(
        route = "home",
        title = "Calculator",
        icon = Icons.Default.Calculate
    )
    object  NumberGenerator: BottomBarScreen(
        route = "numberGenerator",
        title = "Number Generator",
        icon = Icons.Default.Build
    )
}