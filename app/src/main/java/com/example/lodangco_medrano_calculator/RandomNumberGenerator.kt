package com.example.lodangco_medrano_calculator


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt
import kotlin.random.Random

// Number Generator Buttons
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberGeneratorLabel() {
    var randomGeneratedNumber by remember { mutableStateOf(TextFieldValue(" 0 ")) }
    var sliderMaxValue by remember { mutableFloatStateOf(100f) }
    var calculating by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {

        Column (modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp),){
            // Content at the top, e.g., slider
            RandomNumberSlider { maxSliderValue ->
                sliderMaxValue = maxSliderValue
            }
        }
        // Display the generated number
        Text(
            text = randomGeneratedNumber.text,
            style = MaterialTheme.typography.h1.copy(fontWeight = FontWeight.Bold),
            fontSize = 40.sp,
        )

        // Buttons at the bottom
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 58.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Buttons("Generate", Modifier.weight(1f)) {
                    // Generate a random number based on the slider position
                    val randomNumber = generateRandomNumber(sliderMaxValue)
                    randomGeneratedNumber = TextFieldValue(randomNumber.toString())
                    calculating = true
                }

                Spacer(modifier = Modifier.width(16.dp)) // Add a gap between buttons

                Buttons("Reset", Modifier.weight(1f)) {
                    // Implement the reset logic here
                    randomGeneratedNumber = TextFieldValue(" 0 ")
                    calculating = false
                }
            }
        }
    }
}

@Composable
fun RandomNumberSlider(onMaxValueChanged: (Float) -> Unit) {

    var sliderPosition by remember { mutableFloatStateOf(50f) }
    Text(text = sliderPosition.roundToInt().toString())
    Column {
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            valueRange = 0f..100f
        )
        onMaxValueChanged(sliderPosition)
    }
}

fun generateRandomNumber(maxValue: Float): Int {
    // Generate a random number between 0 and the maximum value of the slider
    return Random.nextInt(0, (maxValue + 1).toInt())
}