package com.example.electricityprice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.util.Locale
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CostOfElec(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CostOfElec(modifier: Modifier = Modifier) {
    var consumptionInput by remember { mutableStateOf("") }
    var priceOfKwh by remember { mutableFloatStateOf(0.25f) }
    var VATsmall by remember { mutableStateOf(false) }
    val consumption = consumptionInput.toDoubleOrNull() ?: 0.0
    val roundedPrice = ceil(priceOfKwh * 100) / 100.0
    val cost = consumption * roundedPrice
    val vatPercentage = if (VATsmall) 0.10 else 0.24
    val vatAmount = cost * vatPercentage
    val totalPayment = cost + vatAmount

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Cost of electricity",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = consumptionInput,
            onValueChange = { consumptionInput = it },
            label = { Text("Consumption in kWh") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = String.format(Locale.US, "Price/kWh %.2f €", roundedPrice),
            style = MaterialTheme.typography.bodyLarge
        )

        Slider(
            value = priceOfKwh,
            onValueChange = { priceOfKwh = it },
            valueRange = 0.0f..2.0f,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = VATsmall,
                onCheckedChange = { VATsmall = it }
            )
            Text(
                text = "VAT 10%",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = String.format(Locale.US, "%.2f €", totalPayment),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}