package com.example.remote

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SurreyAcRemoteView(modifier: Modifier = Modifier, viewModel: SurreyAcRemoteViewModel) {
    val buttonPressed = viewModel.buttonPressed

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { viewModel.onOnButtonPressed() }) {
            Text("On")
        }
        Button(onClick = { viewModel.onOffButtonPressed() }) {
            Text("Off")
        }
        if (buttonPressed.isNotEmpty()) {
            Text(text = "tocaste el botón $buttonPressed")
        }
    }
}