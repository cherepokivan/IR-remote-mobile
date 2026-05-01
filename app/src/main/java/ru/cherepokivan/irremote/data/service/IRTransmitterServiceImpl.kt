package ru.cherepokivan.irremote.data.service

import android.content.Context
import android.hardware.ConsumerIrManager
import android.os.Build
import android.util.Log
import ru.cherepokivan.irremote.domain.model.IRCommand
import ru.cherepokivan.irremote.domain.service.IRTransmitterError
import ru.cherepokivan.irremote.domain.service.IRTransmitterService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IRTransmitterServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : IRTransmitterService {

    private val irManager: ConsumerIrManager? by lazy {
        val manager = context.getSystemService(Context.CONSUMER_IR_SERVICE) as? ConsumerIrManager
        Log.d(TAG, "IR Manager initialized: ${manager != null}")
        Log.d(TAG, "Device: ${Build.MANUFACTURER} ${Build.MODEL}")
        if (manager != null) {
            Log.d(TAG, "Has IR Emitter: ${manager.hasIrEmitter()}")
        }
        manager
    }

    // Попытка использовать скрытые API производителей через reflection
    private val vendorIrManager: Any? by lazy {
        try {
            when (Build.MANUFACTURER.lowercase()) {
                "xiaomi", "redmi" -> {
                    Log.d(TAG, "Trying Xiaomi IR API...")
                    val clazz = Class.forName("android.hardware.IrManager")
                    val method = Context::class.java.getMethod("getSystemService", String::class.java)
                    method.invoke(context, "ir")
                }
                "samsung" -> {
                    Log.d(TAG, "Trying Samsung IR API...")
                    val clazz = Class.forName("android.hardware.ConsumerIrManager")
                    context.getSystemService("consumer_ir")
                }
                else -> null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize vendor IR API", e)
            null
        }
    }

    override fun isAvailable(): Boolean {
        // Сначала пробуем стандартный API
        val standardAvailable = irManager?.hasIrEmitter() == true
        if (standardAvailable) {
            Log.d(TAG, "IR Available via standard API: true")
            return true
        }

        // Пробуем vendor API
        val vendorAvailable = vendorIrManager != null
        Log.d(TAG, "IR Available via vendor API: $vendorAvailable")
        return vendorAvailable
    }

    companion object {
        private const val TAG = "IRTransmitterService"
    }

    override fun getCarrierFrequencies(): List<Int> {
        return irManager?.carrierFrequencies?.map { it.minFrequency }?.distinct() ?: emptyList()
    }

    override suspend fun transmit(command: IRCommand): Result<Unit> {
        if (command.pattern.isEmpty()) {
            return Result.failure(IRTransmitterError.TransmissionFailed)
        }

        // Пробуем стандартный API
        val manager = irManager
        if (manager != null && manager.hasIrEmitter()) {
            return try {
                Log.d(TAG, "Transmitting via standard API: freq=${command.frequency}, pattern size=${command.pattern.size}")
                manager.transmit(command.frequency, command.pattern.toIntArray())
                Result.success(Unit)
            } catch (e: Exception) {
                Log.e(TAG, "Standard API transmission failed", e)
                Result.failure(IRTransmitterError.TransmissionFailed)
            }
        }

        // Пробуем vendor API через reflection
        val vendor = vendorIrManager
        if (vendor != null) {
            return try {
                Log.d(TAG, "Transmitting via vendor API: freq=${command.frequency}, pattern size=${command.pattern.size}")
                val method = vendor.javaClass.getMethod(
                    "transmit",
                    Int::class.javaPrimitiveType,
                    IntArray::class.java
                )
                method.invoke(vendor, command.frequency, command.pattern.toIntArray())
                Result.success(Unit)
            } catch (e: Exception) {
                Log.e(TAG, "Vendor API transmission failed", e)
                Result.failure(IRTransmitterError.TransmissionFailed)
            }
        }

        return Result.failure(IRTransmitterError.NotAvailable)
    }
}
