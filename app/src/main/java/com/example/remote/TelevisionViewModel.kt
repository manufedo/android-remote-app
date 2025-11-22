package com.example.remote

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TelevisionViewModel : ViewModel() {
    var buttonPressed by mutableStateOf("")
        private set

    fun onPlusButtonPressed() {
        buttonPressed = "+"
    }

    fun onMinusButtonPressed() {
        buttonPressed = "-"
    }
}
