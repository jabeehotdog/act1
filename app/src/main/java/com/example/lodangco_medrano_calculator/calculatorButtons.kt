package com.example.lodangco_medrano_calculator

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//Drop Down Menu
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OperationOptions(onOperationSelected: (String) -> Unit) {
    val context = LocalContext.current
    val operations = arrayOf("Addition", "Subtraction", "Multiplication", "Division")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(operations[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            label = { Text(text = "Operation") },
            placeholder = { Text(text = "Your Placeholder/Hint") },
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            operations.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        selectedText = item
                        expanded = false
                        Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        onOperationSelected(item) // Call the callback function with the selected operation
                    }
                )
            }
        }
    }
}

@Composable
    fun Buttons(
        string: String,
        modifier: Modifier,
        onClick: () -> Unit,

        ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(top = 20.dp)
                .height(40.dp)
                .clip(CircleShape)
                .background(Color(0xFF1881bd))
                .clickable { onClick() }
                .then(modifier)

        ) {
            Spacer(modifier = Modifier.width(16.dp)) // Add a gap on the left
            Text(
                text = string,
                fontSize = 15.sp,
                color = Color.White

            )
            Spacer(modifier = Modifier.width(16.dp)) // Add a gap on the right
        }
    }

