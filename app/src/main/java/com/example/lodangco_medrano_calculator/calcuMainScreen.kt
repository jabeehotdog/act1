package com.example.lodangco_medrano_calculator


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.ContentAlpha


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) {
        BottomNavGraph(navController = navController)
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Calculator,
        BottomBarScreen.NumberGenerator,

        )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        modifier = Modifier.heightIn(50.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 3.dp
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController,
    ) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}

// TextField
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorLabel() {
    var firstNumberText by remember { mutableStateOf(TextFieldValue(" ")) }
    var secondNumberText by remember { mutableStateOf(TextFieldValue(" ")) }
    var numberResult by remember { mutableStateOf(TextFieldValue(" 0 ")) }
    var calculating by remember { mutableStateOf(false) }
    var selectedOperation by remember { mutableStateOf("Addition") }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        androidx.compose.material3.TextField(
            value = firstNumberText,
            onValueChange = {
                 firstNumberText = it

            },
            label = { Text(text = "First Number:") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        androidx.compose.material3.TextField(
            value = secondNumberText,
            onValueChange = {
                secondNumberText = it

            },
            label = { Text(text = "Second Number:") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        //Drop Down Menu
        OperationOptions { operation ->
            // Update the selected operation
            selectedOperation = operation
        }

        // Display result in the middle of the screen
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 120.dp, bottom = 204.dp),
                contentAlignment = Alignment.Center,
        ) {
            Text(
                text = numberResult.text,
                style = MaterialTheme.typography.h1.copy(fontWeight = FontWeight.Bold),
                fontSize = 40.sp,
            )
        }

        // Add Calculate and Reset buttons in a single row at the bottom
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Buttons("Calculate", Modifier.weight(1f)) {
                // Implement the calculation logic here
                numberResult = TextFieldValue(
                    calculateResult(
                        firstNumberText.text,
                        secondNumberText.text,
                        selectedOperation
                    )
                )
                calculating = true
                // Add your calculation logic here
            }

            Spacer(modifier = Modifier.width(16.dp)) // Add a gap between buttons

            Buttons("Reset", Modifier.weight(1f)) {
                // Implement the reset logic here
                firstNumberText = TextFieldValue(" ")
                secondNumberText = TextFieldValue(" ")
                numberResult = TextFieldValue(" 0 ")
                calculating = false
            }
        }
    }
}



// Function to calculate the result based on the selected operation
private fun calculateResult(firstNumber: String, secondNumber: String, operation: String): String {
    return try {
        val num1 = firstNumber.toDouble()
        val num2 = secondNumber.toDouble()

        // Check if both num1 and num2 are within the allowed range
        if (num1 > 99999 || num2 > 99999) {
            return "Values exceed the maximum allowed limit (99,999)"
        }

        // Perform the calculation based on the selected operation
        val result = when (operation) {
            "Addition" -> num1 + num2
            "Subtraction" -> num1 - num2
            "Multiplication" -> num1 * num2
            "Division" -> if (num2 != 0.0) num1 / num2 else 0.0
            else -> 0.0
        }

        // Format the result based on whether both inputs are decimals
        val formattedResult = if (result % 1 == 0.0) {
            // If the result is an exact integer, omit decimal values
            result.toInt().toString()
        } else {
            // If the result has decimal values, display them
            result.toString()
        }

        formattedResult
    } catch (e: NumberFormatException) {
        // Handle the case where input is not a valid number
        "Invalid Input"
    }
}


