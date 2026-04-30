package com.example.irremote.data.local

import com.example.irremote.domain.model.Device
import com.example.irremote.domain.model.DeviceType
import com.example.irremote.domain.model.IRCommand
import com.example.irremote.domain.model.IRProtocol

object SampleDataProvider {

    fun getSampleDevices(): List<Pair<Device, List<IRCommand>>> {
        return listOf(
            getSamsungTV(),
            getDaikinAC(),
            getXiaomiFan()
        )
    }

    private fun getSamsungTV(): Pair<Device, List<IRCommand>> {
        val device = Device(
            name = "Samsung TV",
            type = DeviceType.TV,
            brand = "Samsung",
            model = "UE55",
            isFavorite = true
        )

        val commands = listOf(
            IRCommand(
                deviceId = 0,
                name = "Power",
                protocol = IRProtocol.SAMSUNG,
                frequency = 38000,
                pattern = listOf(4500, 4500, 560, 1690, 560, 1690, 560, 560, 560, 560, 560)
            ),
            IRCommand(
                deviceId = 0,
                name = "Vol+",
                protocol = IRProtocol.SAMSUNG,
                frequency = 38000,
                pattern = listOf(4500, 4500, 560, 560, 560, 1690, 560, 560, 560, 1690, 560)
            ),
            IRCommand(
                deviceId = 0,
                name = "Vol-",
                protocol = IRProtocol.SAMSUNG,
                frequency = 38000,
                pattern = listOf(4500, 4500, 560, 1690, 560, 560, 560, 560, 560, 1690, 560)
            ),
            IRCommand(
                deviceId = 0,
                name = "Ch+",
                protocol = IRProtocol.SAMSUNG,
                frequency = 38000,
                pattern = listOf(4500, 4500, 560, 560, 560, 560, 560, 1690, 560, 560, 560)
            ),
            IRCommand(
                deviceId = 0,
                name = "Ch-",
                protocol = IRProtocol.SAMSUNG,
                frequency = 38000,
                pattern = listOf(4500, 4500, 560, 1690, 560, 560, 560, 1690, 560, 560, 560)
            ),
            IRCommand(
                deviceId = 0,
                name = "Mute",
                protocol = IRProtocol.SAMSUNG,
                frequency = 38000,
                pattern = listOf(4500, 4500, 560, 560, 560, 1690, 560, 1690, 560, 560, 560)
            ),
            IRCommand(
                deviceId = 0,
                name = "Menu",
                protocol = IRProtocol.SAMSUNG,
                frequency = 38000,
                pattern = listOf(4500, 4500, 560, 1690, 560, 1690, 560, 560, 560, 1690, 560)
            ),
            IRCommand(
                deviceId = 0,
                name = "OK",
                protocol = IRProtocol.SAMSUNG,
                frequency = 38000,
                pattern = listOf(4500, 4500, 560, 560, 560, 560, 560, 560, 560, 1690, 560)
            ),
            IRCommand(
                deviceId = 0,
                name = "Back",
                protocol = IRProtocol.SAMSUNG,
                frequency = 38000,
                pattern = listOf(4500, 4500, 560, 1690, 560, 560, 560, 1690, 560, 1690, 560)
            ),
            IRCommand(
                deviceId = 0,
                name = "Home",
                protocol = IRProtocol.SAMSUNG,
                frequency = 38000,
                pattern = listOf(4500, 4500, 560, 560, 560, 1690, 560, 560, 560, 560, 560)
            )
        )

        return device to commands
    }

    private fun getDaikinAC(): Pair<Device, List<IRCommand>> {
        val device = Device(
            name = "Daikin AC",
            type = DeviceType.AC,
            brand = "Daikin",
            model = "FTXS35",
            isFavorite = false
        )

        val commands = listOf(
            IRCommand(
                deviceId = 0,
                name = "Power",
                protocol = IRProtocol.RAW,
                frequency = 38000,
                pattern = listOf(3400, 1750, 420, 1300, 420, 450, 420, 450, 420)
            ),
            IRCommand(
                deviceId = 0,
                name = "Temp+",
                protocol = IRProtocol.RAW,
                frequency = 38000,
                pattern = listOf(3400, 1750, 420, 450, 420, 1300, 420, 450, 420)
            ),
            IRCommand(
                deviceId = 0,
                name = "Temp-",
                protocol = IRProtocol.RAW,
                frequency = 38000,
                pattern = listOf(3400, 1750, 420, 1300, 420, 450, 420, 1300, 420)
            ),
            IRCommand(
                deviceId = 0,
                name = "Mode",
                protocol = IRProtocol.RAW,
                frequency = 38000,
                pattern = listOf(3400, 1750, 420, 450, 420, 450, 420, 1300, 420)
            ),
            IRCommand(
                deviceId = 0,
                name = "Fan",
                protocol = IRProtocol.RAW,
                frequency = 38000,
                pattern = listOf(3400, 1750, 420, 1300, 420, 1300, 420, 450, 420)
            ),
            IRCommand(
                deviceId = 0,
                name = "Swing",
                protocol = IRProtocol.RAW,
                frequency = 38000,
                pattern = listOf(3400, 1750, 420, 450, 420, 1300, 420, 1300, 420)
            )
        )

        return device to commands
    }

    private fun getXiaomiFan(): Pair<Device, List<IRCommand>> {
        val device = Device(
            name = "Xiaomi Fan",
            type = DeviceType.FAN,
            brand = "Xiaomi",
            model = "Mi Smart",
            isFavorite = false
        )

        val commands = listOf(
            IRCommand(
                deviceId = 0,
                name = "Power",
                protocol = IRProtocol.NEC,
                frequency = 38000,
                pattern = listOf(9000, 4500, 560, 560, 560, 1690, 560, 560, 560)
            ),
            IRCommand(
                deviceId = 0,
                name = "Speed",
                protocol = IRProtocol.NEC,
                frequency = 38000,
                pattern = listOf(9000, 4500, 560, 1690, 560, 560, 560, 1690, 560)
            ),
            IRCommand(
                deviceId = 0,
                name = "Oscillate",
                protocol = IRProtocol.NEC,
                frequency = 38000,
                pattern = listOf(9000, 4500, 560, 560, 560, 1690, 560, 1690, 560)
            )
        )

        return device to commands
    }
}
