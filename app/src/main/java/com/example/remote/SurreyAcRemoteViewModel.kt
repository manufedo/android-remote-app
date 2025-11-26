package com.example.remote

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class SurreyAcRemoteViewModel(private val _infraredTransmitter: InfraredTransmitter){

    private val COLD_COMMAND : Long = 0xB24D9F60000F
    private val OFF_COMMAND : Long = 0xB24D7B84E01F
    private val shortDuration = 550
    private val zeroDuration = shortDuration
    private val oneDuration = shortDuration*3;
    private val longDuration = shortDuration*8;

    //Esto está hecho usando alguna variante del código gray
    private val temperatureCode : Map<Int,Long> = hashMapOf(
        17 to 0x00F0,
        18 to 0x10E0,
        19 to 0x30C0,
        20 to 0x20D0,
        21 to 0x6090,
        22 to 0x7080,
        23 to 0x50A0,
        24 to 0x40B0,
        25 to 0xC030,
        26 to 0xD020,
        27 to 0x9060,
        28 to 0x8070,
        29 to 0xA050,
        30 to 0xB040
    )
    var buttonPressed by mutableStateOf("")
        private set

    private var _temperature = mutableStateOf(17)

    var temperature: Int
        get() = _temperature.value
        set(value) {
            // Solo actualizamos y transmitimos si el valor es válido y diferente
            if (value in 17..30 && value != _temperature.value) {
                _temperature.value = value
            }
            setTemperature()
        }

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
        setTemperature()
    }

    fun onOffButtonPressed() {
        buttonPressed = "off"
         transmitCommand(OFF_COMMAND)
    }

    private fun setTemperature(){
        val command = COLD_COMMAND or temperatureCode[temperature]!!
        transmitCommand(command)
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
