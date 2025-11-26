package com.example.remote

interface InfraredTransmitter {
    fun transmit(frequency: Int, pattern: IntArray)
}