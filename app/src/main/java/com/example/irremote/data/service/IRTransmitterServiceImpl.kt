package com.example.irremote.data.service

import android.content.Context
import android.hardware.ConsumerIrManager
import com.example.irremote.domain.model.IRCommand
import com.example.irremote.domain.service.IRTransmitterError
import com.example.irremote.domain.service.IRTransmitterService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class IRTransmitterServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : IRTransmitterService {

    private val irManager: ConsumerIrManager? by lazy {
        context.getSystemService(Context.CONSUMER_IR_SERVICE) as? ConsumerIrManager
    }

    override fun isAvailable(): Boolean {
        return irManager?.hasIrEmitter() == true
    }

    override fun getCarrierFrequencies(): List<Int> {
        return irManager?.carrierFrequencies?.map { it.minFrequency }?.distinct() ?: emptyList()
    }

    override suspend fun transmit(command: IRCommand): Result<Unit> {
        val manager = irManager ?: return Result.failure(IRTransmitterError.NotAvailable)

        if (!manager.hasIrEmitter()) {
            return Result.failure(IRTransmitterError.NotAvailable)
        }

        return try {
            manager.transmit(command.frequency, command.pattern.toIntArray())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(IRTransmitterError.TransmissionFailed)
        }
    }
}
