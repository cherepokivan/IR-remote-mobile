package com.irremote.app.domain.service

import com.irremote.app.domain.model.IRCommand

interface IRTransmitterService {
    fun isAvailable(): Boolean

    fun getCarrierFrequencies(): IntArray?

    suspend fun transmit(command: IRCommand): Result<Unit>

    suspend fun transmit(frequency: Int, pattern: IntArray): Result<Unit>
}

sealed class IRTransmitterError : Exception() {
    object NotAvailable : IRTransmitterError()
    object InvalidFrequency : IRTransmitterError()
    object InvalidPattern : IRTransmitterError()
    data class TransmissionFailed(val reason: String) : IRTransmitterError()
}
