package com.example.remote

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class SurreyAcRemoteViewModel(private val _infraredEmitter: InfraredEmitter){

    private val ON_COMMAND = "B24D9F6040BF"
    private val OFF_COMMAND = "B24D7B84E01F"
    var buttonPressed by mutableStateOf("")
        private set

    fun onOnButtonPressed() {
        buttonPressed = "on"
    }

    fun onOffButtonPressed() {
        buttonPressed = "off"
        val pattern = intArrayOf(1901, 4453, 625, 1568, 625, 1568, 625, 502, 625, 502, 625, 502, 625, 502, 625, 502, 625, 1568, 625, 1568, 625, 1568, 625, 502, 625, 502, 625, 502, 625, 502, 625, 502, 625, 1568, 625, 1568, 625, 502, 625, 502, 625, 502, 625, 1568, 625, 502, 625, 502, 625, 1568, 625, 502, 625, 1568, 625, 1568, 625, 40000)
        val frequency = 38400
        _infraredEmitter.transmit(frequency, pattern)
    }
}
