package com.irremote.app.domain.usecase

import com.irremote.app.domain.model.IRCommand
import com.irremote.app.domain.service.IRTransmitterService
import javax.inject.Inject

class TransmitIRCommandUseCase @Inject constructor(
    private val irTransmitterService: IRTransmitterService
) {
    suspend operator fun invoke(command: IRCommand): Result<Unit> {
        return irTransmitterService.transmit(command)
    }
}

class CheckIRAvailabilityUseCase @Inject constructor(
    private val irTransmitterService: IRTransmitterService
) {
    operator fun invoke(): Boolean {
        return irTransmitterService.isAvailable()
    }
}

class GetCarrierFrequenciesUseCase @Inject constructor(
    private val irTransmitterService: IRTransmitterService
) {
    operator fun invoke(): IntArray? {
        return irTransmitterService.getCarrierFrequencies()
    }
}
