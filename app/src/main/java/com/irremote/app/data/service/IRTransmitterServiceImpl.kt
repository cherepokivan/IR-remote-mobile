package com.irremote.app.data.service

import android.content.Context
import android.hardware.ConsumerIrManager
import com.irremote.app.domain.model.IRCommand
import com.irremote.app.domain.service.IRTransmitterError
import com.irremote.app.domain.service.IRTransmitterService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IRTransmitterServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : IRTransmitterService {

    private val consumerIrManager: ConsumerIrManager? by lazy {
        context.getSystemService(Context.CONSUMER_IR_SERVICE) as? ConsumerIrManager
    }

    override fun isAvailable(): Boolean {
        return consumerIrManager?.hasIrEmitter() == true
    }

    override fun getCarrierFrequencies(): IntArray? {
        return consumerIrManager?.carrierFrequencies?.map { it.minFrequency }?.toIntArray()
    }

    override suspend fun transmit(command: IRCommand): Result<Unit> {
        return transmit(command.frequency, command.pattern.toIntArray())
    }

    override suspend fun transmit(frequency: Int, pattern: IntArray): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val irManager = consumerIrManager
                    ?: return@withContext Result.failure(IRTransmitterError.NotAvailable)

                if (!irManager.hasIrEmitter()) {
                    return@withContext Result.failure(IRTransmitterError.NotAvailable)
                }

                if (frequency <= 0) {
                    return@withContext Result.failure(IRTransmitterError.InvalidFrequency)
                }

                if (pattern.isEmpty()) {
                    return@withContext Result.failure(IRTransmitterError.InvalidPattern)
                }

                irManager.transmit(frequency, pattern)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(IRTransmitterError.TransmissionFailed(e.message ?: "Unknown error"))
            }
        }
    }
}
