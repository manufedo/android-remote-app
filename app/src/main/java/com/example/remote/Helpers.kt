package com.example.remote

internal object Helpers {


    fun longToBinaryArray(number: Long): BooleanArray {
        val booleanArray = BooleanArray(64)

        // Recorremos desde el bit 63 (izquierda) hasta el 0 (derecha)
        for (i in 0 until 64) {
            // Movemos los bits a la derecha para dejar el bit que nos interesa en la posición 0
            // Ejemplo: Si i es 0, miramos el bit 63 (el primero de la izquierda)
            val bitPosition = 63 - i

            // (number shr bitPosition) mueve el bit a la posición 0
            // ( ... and 1L) aísla ese bit. Si es 1, el resultado es 1.
            val bitValue = (number shr bitPosition) and 1L

            // Si bitValue es 1, es true. Si es 0, es false.
            booleanArray[i] = (bitValue == 1L)
        }

        return booleanArray
    }
}
