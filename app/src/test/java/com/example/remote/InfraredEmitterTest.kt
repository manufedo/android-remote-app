package com.example.remote

import org.junit.Assert.assertArrayEquals
import org.junit.Test

class InfraredEmitterTest {

    @Test
    fun hexToBinaryArray_convertsCorrectly() {
        val hex = "B2"
        val expected = intArrayOf(1, 0, 1, 1, 0, 0, 1, 0)
        val result = InfraredEmitter.hexToBinaryArray(hex)
        assertArrayEquals(expected, result)
    }
}
