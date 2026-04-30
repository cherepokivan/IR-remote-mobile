package ru.cherepokivan.irremote.domain.usecase

import ru.cherepokivan.irremote.domain.model.IRCommand
import ru.cherepokivan.irremote.domain.service.IRTransmitterService
import javax.inject.Inject

data class IRTransmitterUseCases(
    val transmitIRCommand: TransmitIRCommandUseCase,
    val checkIRAvailability: CheckIRAvailabilityUseCase,
    val getCarrierFrequencies: GetCarrierFrequenciesUseCase
)

class TransmitIRCommandUseCase @Inject constructor(
    private val service: IRTransmitterService
) {
    suspend operator fun invoke(command: IRCommand): Result<Unit> {
        return service.transmit(command)
    }
}

class CheckIRAvailabilityUseCase @Inject constructor(
    private val service: IRTransmitterService
) {
    operator fun invoke(): Boolean {
        return service.isAvailable()
    }
}

class GetCarrierFrequenciesUseCase @Inject constructor(
    private val service: IRTransmitterService
) {
    operator fun invoke(): List<Int> {
        return service.getCarrierFrequencies()
    }
}
