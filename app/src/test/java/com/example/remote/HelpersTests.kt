package com.example.remote

import org.junit.Assert.assertArrayEquals
import org.junit.Test

class HelpersTests {

    @Test
    fun hexToBinaryArray_convertsCorrectly() {
        val hex = "B2"
        val expected = booleanArrayOf(true, false, true, true, false, false, true, false)
        val result = Helpers.hexToBinaryArray(hex)
        assertArrayEquals(expected, result)
    }
}
