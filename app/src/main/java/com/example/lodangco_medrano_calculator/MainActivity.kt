package com.example.lodangco_medrano_calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.lodangco_medrano_calculator.ui.theme.Lodangco_Medrano_CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lodangco_Medrano_CalculatorTheme {
                MainScreen()
            }
        }
    }
}



