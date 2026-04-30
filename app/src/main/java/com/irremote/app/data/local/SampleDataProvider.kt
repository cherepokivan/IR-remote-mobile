package com.irremote.app.data.local

import com.irremote.app.domain.model.Device
import com.irremote.app.domain.model.DeviceType
import com.irremote.app.domain.model.IRCommand
import com.irremote.app.domain.model.IRProtocol
import com.irremote.app.domain.repository.DeviceRepository
import com.irremote.app.domain.repository.IRCommandRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SampleDataProvider @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val irCommandRepository: IRCommandRepository
) {
    suspend fun insertSampleData() {
        // Проверяем, есть ли уже данные
        val devices = mutableListOf<Device>()
        deviceRepository.getAllDevices().collect { existingDevices ->
            devices.addAll(existingDevices)
        }

        if (devices.isNotEmpty()) {
            return // Данные уже есть
        }

        // Добавляем тестовые устройства
        val tvId = deviceRepository.insertDevice(
            Device(
                name = "Телевизор Samsung",
                type = DeviceType.TV,
                brand = "Samsung",
                model = "UE55TU7100",
                isFavorite = true
            )
        )

        val acId = deviceRepository.insertDevice(
            Device(
                name = "Кондиционер Daikin",
                type = DeviceType.AC,
                brand = "Daikin",
                model = "FTXS35K",
                isFavorite = true
            )
        )

        val fanId = deviceRepository.insertDevice(
            Device(
                name = "Вентилятор",
                type = DeviceType.FAN,
                brand = "Xiaomi",
                model = null,
                isFavorite = false
            )
        )

        // Добавляем тестовые команды для телевизора
        val tvCommands = listOf(
            IRCommand(
                deviceId = tvId,
                name = "POWER",
                protocol = IRProtocol.SAMSUNG,
                frequency = 38000,
                pattern = listOf(4500, 4500, 560, 1690, 560, 1690, 560, 1690, 560, 560, 560, 560, 560, 560, 560, 560, 560, 560)
            ),
            IRCommand(
                deviceId = tvId,
                name = "VOL_UP",
                protocol = IRProtocol.SAMSUNG,
                frequency = 38000,
                pattern = listOf(4500, 4500, 560, 1690, 560, 1690, 560, 560, 560, 1690, 560, 560, 560, 560, 560, 560, 560, 560)
            ),
            IRCommand(
                deviceId = tvId,
                name = "VOL_DOWN",
                protocol = IRProtocol.SAMSUNG,
                frequency = 38000,
                pattern = listOf(4500, 4500, 560, 1690, 560, 1690, 560, 560, 560, 560, 560, 1690, 560, 560, 560, 560, 560, 560)
            ),
            IRCommand(
                deviceId = tvId,
                name = "CH_UP",
                protocol = IRProtocol.SAMSUNG,
                frequency = 38000,
                pattern = listOf(4500, 4500, 560, 1690, 560, 560, 560, 1690, 560, 1690, 560, 560, 560, 560, 560, 560, 560, 560)
            ),
            IRCommand(
                deviceId = tvId,
                name = "CH_DOWN",
                protocol = IRProtocol.SAMSUNG,
                frequency = 38000,
                pattern = listOf(4500, 4500, 560, 1690, 560, 560, 560, 1690, 560, 560, 560, 1690, 560, 560, 560, 560, 560, 560)
            ),
            IRCommand(
                deviceId = tvId,
                name = "MUTE",
                protocol = IRProtocol.SAMSUNG,
                frequency = 38000,
                pattern = listOf(4500, 4500, 560, 1690, 560, 1690, 560, 1690, 560, 1690, 560, 560, 560, 560, 560, 560, 560, 560)
            ),
            IRCommand(
                deviceId = tvId,
                name = "MENU",
                protocol = IRProtocol.SAMSUNG,
                frequency = 38000,
                pattern = listOf(4500, 4500, 560, 560, 560, 1690, 560, 1690, 560, 560, 560, 1690, 560, 560, 560, 560, 560, 560)
            ),
            IRCommand(
                deviceId = tvId,
                name = "OK",
                protocol = IRProtocol.SAMSUNG,
                frequency = 38000,
                pattern = listOf(4500, 4500, 560, 560, 560, 1690, 560, 560, 560, 1690, 560, 1690, 560, 560, 560, 560, 560, 560)
            ),
            IRCommand(
                deviceId = tvId,
                name = "UP",
                protocol = IRProtocol.SAMSUNG,
                frequency = 38000,
                pattern = listOf(4500, 4500, 560, 560, 560, 1690, 560, 560, 560, 1690, 560, 560, 560, 1690, 560, 560, 560, 560)
            ),
            IRCommand(
                deviceId = tvId,
                name = "DOWN",
                protocol = IRProtocol.SAMSUNG,
                frequency = 38000,
                pattern = listOf(4500, 4500, 560, 560, 560, 1690, 560, 560, 560, 1690, 560, 560, 560, 560, 560, 1690, 560, 560)
            ),
            IRCommand(
                deviceId = tvId,
                name = "LEFT",
                protocol = IRProtocol.SAMSUNG,
                frequency = 38000,
                pattern = listOf(4500, 4500, 560, 560, 560, 1690, 560, 560, 560, 560, 560, 1690, 560, 1690, 560, 560, 560, 560)
            ),
            IRCommand(
                deviceId = tvId,
                name = "RIGHT",
                protocol = IRProtocol.SAMSUNG,
                frequency = 38000,
                pattern = listOf(4500, 4500, 560, 560, 560, 1690, 560, 560, 560, 560, 560, 1690, 560, 560, 560, 1690, 560, 560)
            )
        )

        irCommandRepository.insertCommands(tvCommands)

        // Добавляем тестовые команды для кондиционера
        val acCommands = listOf(
            IRCommand(
                deviceId = acId,
                name = "POWER",
                protocol = IRProtocol.DAIKIN,
                frequency = 38000,
                pattern = listOf(3400, 1750, 420, 1300, 420, 450, 420, 450, 420, 450, 420, 450, 420, 450, 420, 450, 420, 450)
            ),
            IRCommand(
                deviceId = acId,
                name = "TEMP_UP",
                protocol = IRProtocol.DAIKIN,
                frequency = 38000,
                pattern = listOf(3400, 1750, 420, 1300, 420, 450, 420, 1300, 420, 450, 420, 450, 420, 450, 420, 450, 420, 450)
            ),
            IRCommand(
                deviceId = acId,
                name = "TEMP_DOWN",
                protocol = IRProtocol.DAIKIN,
                frequency = 38000,
                pattern = listOf(3400, 1750, 420, 1300, 420, 450, 420, 450, 420, 1300, 420, 450, 420, 450, 420, 450, 420, 450)
            ),
            IRCommand(
                deviceId = acId,
                name = "MODE",
                protocol = IRProtocol.DAIKIN,
                frequency = 38000,
                pattern = listOf(3400, 1750, 420, 1300, 420, 1300, 420, 1300, 420, 450, 420, 450, 420, 450, 420, 450, 420, 450)
            )
        )

        irCommandRepository.insertCommands(acCommands)

        // Добавляем тестовые команды для вентилятора
        val fanCommands = listOf(
            IRCommand(
                deviceId = fanId,
                name = "POWER",
                protocol = IRProtocol.NEC,
                frequency = 38000,
                pattern = listOf(9000, 4500, 560, 560, 560, 560, 560, 1690, 560, 560, 560, 560, 560, 560, 560, 560, 560, 560)
            ),
            IRCommand(
                deviceId = fanId,
                name = "SPEED_UP",
                protocol = IRProtocol.NEC,
                frequency = 38000,
                pattern = listOf(9000, 4500, 560, 560, 560, 1690, 560, 1690, 560, 560, 560, 560, 560, 560, 560, 560, 560, 560)
            ),
            IRCommand(
                deviceId = fanId,
                name = "SPEED_DOWN",
                protocol = IRProtocol.NEC,
                frequency = 38000,
                pattern = listOf(9000, 4500, 560, 560, 560, 1690, 560, 560, 560, 1690, 560, 560, 560, 560, 560, 560, 560, 560)
            )
        )

        irCommandRepository.insertCommands(fanCommands)
    }
}
