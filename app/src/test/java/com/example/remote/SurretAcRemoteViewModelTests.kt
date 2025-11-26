package com.example.remote

import org.junit.Assert.assertArrayEquals
import org.junit.Test

class SurretAcRemoteViewModelTests {
    private class FakeInfraredTransmitter : InfraredTransmitter {

        var lastEmittedMessage = intArrayOf(0)

        override fun transmit(frequency: Int, pattern: IntArray) {
            lastEmittedMessage = pattern
        }

    }

    @Test
    fun transmmitsCommandCorrectly(){
        val irTransmitter = FakeInfraredTransmitter()
        val viewModel = SurreyAcRemoteViewModel(irTransmitter)

        val command : Long = 0xF0
        val pattern  = mutableListOf<Int>();

        //El primer espacio
        pattern.addAll(listOf(4400,4400))
        //Cuatro unos
        pattern.addAll(listOf(550,1650,550,1650,550,1650,550,1650))
        //Cuatro ceros
        pattern.addAll(listOf(550,550,550,550,550,550,550,550))
        //Separador del mensaje
        pattern.addAll(listOf(550,5000,4400,4400))
        //Cuatro unos
        pattern.addAll(listOf(550,1650,550,1650,550,1650,550,1650))
        //Cuatro ceros
        pattern.addAll(listOf(550,550,550,550,550,550,550,550))

        //Para que entienda que se apagó por última vez
        pattern.add(40000)

        viewModel.transmitCommand(command)

        //assertArrayEquals(pattern.toIntArray(),irTransmitter.lastEmittedMessage)

    }


}