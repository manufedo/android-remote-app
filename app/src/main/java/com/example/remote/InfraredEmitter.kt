package com.example.remote

import android.hardware.ConsumerIrManager

class InfraredEmitter (private val _irManager: ConsumerIrManager?) {

    fun transmit(frequency: Int, pattern: IntArray) {
        if (_irManager?.hasIrEmitter() == true) {
            _irManager.transmit(frequency, pattern)
        }
    }

    /**
     * Converts a hexadecimal string into an array of integers representing binary (0s and 1s).
     * For example, "B2" would be converted to [1, 0, 1, 1, 0, 0, 1, 0].
     */
    fun hexToBinaryArray(hex: String): IntArray {
        val binaryList = mutableListOf<Int>()
        for (hexChar in hex) {
            // Convert hex character to integer
            val intValue = hexChar.toString().toInt(16)
            // Convert integer to a 4-digit binary string (e.g., 11 -> "1011")
            val binaryString = intValue.toString(2).padStart(4, '0')
            // Add each digit to the list
            for (binaryChar in binaryString) {
                binaryList.add(Character.getNumericValue(binaryChar))
            }
        }
        return binaryList.toIntArray()
    }
    
}
