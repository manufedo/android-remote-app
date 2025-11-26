package com.example.remote

import org.junit.Assert.assertArrayEquals
import org.junit.Test

class HelpersTests {

    @Test
    fun hexToBinaryArray_convertsCorrectly() {
        val hex : Long = 0xB2
        val expected = mutableListOf<Boolean>();
        for (i in 0 until 56)
        {
            expected.add(false)
        }
        expected.addAll(listOf(true,false,true,true,false,false,true,false))
        val result = Helpers.longToBinaryArray(hex)
        assertArrayEquals(expected.toBooleanArray(), result)
    }
}
