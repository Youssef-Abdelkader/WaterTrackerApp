package com.youssef.waterapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    currentTotalWaterAmount: Int,
    onTotalWaterAmountUpdated: (Int) -> Unit,
    onResetBottle: () -> Unit


) {
    var ageInput by remember { mutableStateOf("") }
    var weightInput by remember { mutableStateOf("") }
    var heightInput by remember { mutableStateOf("") }
    var calculationError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Water Intake Calculator",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Input Fields
        OutlinedTextField(
            value = ageInput,
            onValueChange = { ageInput = it.filter { char -> char.isDigit() } },
            label = { Text("Age (years)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = weightInput,
            onValueChange = { weightInput = it.filter { char -> char.isDigit() } },
            label = { Text("Weight (kg)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = heightInput,
            onValueChange = { heightInput = it.filter { char -> char.isDigit() } },
            label = { Text("Height (cm)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        if (calculationError) {
            Text(
                text = "Please enter valid numbers in all fields",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Button Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    ageInput = ""
                    weightInput = ""
                    heightInput = ""
                    calculationError = false

                    onTotalWaterAmountUpdated(2500)  // Reset goal to default
                    onResetBottle()                  // Clear the bottle
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Reset All & Clear Bottle")
            }

            // .

            Spacer(modifier = Modifier.width(16.dp))

            // Calculate Button
            Button(
                onClick = {
                    val age = ageInput.toIntOrNull()
                    val weight = weightInput.toIntOrNull()
                    val height = heightInput.toIntOrNull()

                    if (age == null || weight == null || height == null) {
                        calculationError = true
                    } else {
                        calculationError = false
                        val calculatedWater = (weight * 35) + (height * 0.5).toInt() - (age * 2)
                        onTotalWaterAmountUpdated(calculatedWater.coerceAtLeast(500))
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Calculate")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Recommended Daily Intake: ${currentTotalWaterAmount}ml",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Manual Override Section
        Text(
            text = "Manual Override",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        var manualInput by remember { mutableStateOf(currentTotalWaterAmount.toString()) }

        OutlinedTextField(
            value = manualInput,
            onValueChange = { manualInput = it.filter { char -> char.isDigit() } },
            label = { Text("Direct Input (ml)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                manualInput.toIntOrNull()?.let {
                    if (it > 0) onTotalWaterAmountUpdated(it)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Set Manual Value")
        }
    }
}