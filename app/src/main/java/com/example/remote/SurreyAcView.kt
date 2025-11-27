package com.example.remote

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SurreyAcRemoteView(modifier: Modifier = Modifier, viewModel: SurreyAcRemoteViewModel) {

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ){
            Text(
                text = "${viewModel.temperature}°",
                modifier = modifier.align(Alignment.BottomCenter)
            )

        }
        Box(
            modifier = Modifier
                .weight(2f)
                .fillMaxSize()
        ){
            Column(
                modifier = Modifier.fillMaxSize()
            ){
                Row(
                    modifier = Modifier
                        .weight(1f)
                ){
                    StandardButton(onClick = { viewModel.onOnButtonPressed() }, text = "On")
                    StandardButton(onClick = { viewModel.onOffButtonPressed() }, text = "Off")
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .weight(1f)
                ){
                    StandardButton(onClick = { viewModel.onTemperatureUp() }, text = "+")
                    StandardButton(onClick = { viewModel.onTemperatureDown() }, text = "-")
                }
            }
        }
    }
}

@Composable
fun RowScope.StandardButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .weight(1f)
            .align(Alignment.CenterVertically),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onClick
            //modifier =  Modifier.align(Alignment.Center)
        ) {
            Text(text)
        }
    }
}