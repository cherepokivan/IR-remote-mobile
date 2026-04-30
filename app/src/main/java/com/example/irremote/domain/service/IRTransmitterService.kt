package com.example.irremote.domain.service

import com.example.irremote.domain.model.IRCommand

interface IRTransmitterService {
    fun isAvailable(): Boolean
    fun getCarrierFrequencies(): List<Int>
    suspend fun transmit(command: IRCommand): Result<Unit>
}

sealed class IRTransmitterError : Exception() {
    object NotAvailable : IRTransmitterError()
    object InvalidFrequency : IRTransmitterError()
    object TransmissionFailed : IRTransmitterError()
}
