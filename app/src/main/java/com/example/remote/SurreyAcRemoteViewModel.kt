package com.example.remote

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class SurreyAcRemoteViewModel(private val _infraredTransmitter: InfraredTransmitter){

    private val ON_COMMAND : Long = 0xB24D9F6040BF
    private val OFF_COMMAND : Long = 0xB24D7B84E01F
    private val shortDuration = 550
    private val zeroDuration = shortDuration
    private val oneDuration = shortDuration*3;
    private val longDuration = shortDuration*8;
    var buttonPressed by mutableStateOf("")
        private set

    var temperature by mutableStateOf(17)
        private set

    fun onTemperatureUp() {
        if (temperature < 30) { // Assuming 30 is the max temp
            temperature++
        }
    }

    fun onTemperatureDown() {
        if (temperature > 17) { // Assuming 16 is the min temp
            temperature--
        }
    }

    fun onOnButtonPressed() {
        buttonPressed = "on"
        transmitCommand(ON_COMMAND)
    }

    fun onOffButtonPressed() {
        buttonPressed = "off"
         transmitCommand(OFF_COMMAND)
    }

     fun transmitCommand(command : Long){
        val pattern = mutableListOf<Int>()

        //Agrego 2 espacios largos antes de empezar a emitir el mensaje para avisarle al aire acondicionado (siguiendo su protocolo)
        pattern.addAll(listOf(longDuration, longDuration))

        //Paso el comando a unos y ceros (true y false)
        val binaryCommand = Helpers.longToBinaryArray(command)

        //Pusheo el mensaje
        pushCommand(pattern, binaryCommand)

        //Agrego un espacio corto y tres largos para indicar que terminé de enviar el mensaje
        pattern.addAll(listOf(shortDuration, longDuration, longDuration, longDuration))

        //Mando el comando de nuevo (siguiendo el protocolo del aire obvio)
        pushCommand(pattern, binaryCommand)

         pattern.add(40000)

        //Transmito el mensaje
        _infraredTransmitter.transmit(38400, pattern.toIntArray())
    }

    private fun pushCommand(pattern : MutableList<Int>, binaryCommand : BooleanArray) {
        //Solo necesito 48 bits no los 64
        for (i in 16 until 64)
        {
            val bit = binaryCommand[i]
            //Agrego un espacio delante de cada bit (es el momento que la luz se prende)
            pattern.add(shortDuration)
            if (bit)
                pattern.add(oneDuration)
            else
                pattern.add(zeroDuration)
        }
    }
}
