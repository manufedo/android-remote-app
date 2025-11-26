package com.example.remote

internal object Helpers {
    /**
     * Converts a hexadecimal string into an array of booleans representing binary (0s and 1s).
     */
    fun hexToBinaryArray(hex: String): BooleanArray {
        val binaryList = mutableListOf<Boolean>()
        for (hexChar in hex) {
            // Convert hex character to integer
            val intValue = hexChar.toString().toInt(16)
            // Convert integer to a 4-digit binary string (e.g., 11 -> "1011")
            val binaryString = intValue.toString(2).padStart(4, '0')
            // Add each digit to the list
            for (binaryChar in binaryString) {
                binaryList.add(binaryChar == '1')
            }
        }
        return binaryList.toBooleanArray()
    }
}
