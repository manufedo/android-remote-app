package com.example.remote

import android.hardware.ConsumerIrManager

class AndroidIrTransmitter (private val _irManager: ConsumerIrManager?) : InfraredTransmitter {

    override fun transmit(frequency: Int, pattern: IntArray) {
        if (_irManager?.hasIrEmitter() == true) {
            _irManager.transmit(frequency, pattern)
        }
    }

}
