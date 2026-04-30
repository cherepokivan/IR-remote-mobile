package ru.cherepokivan.irremote.data.mapper

import ru.cherepokivan.irremote.data.remote.dto.IRDBCommandDto
import ru.cherepokivan.irremote.data.remote.dto.IRDBDeviceDto
import ru.cherepokivan.irremote.domain.model.Device
import ru.cherepokivan.irremote.domain.model.DeviceType
import ru.cherepokivan.irremote.domain.model.IRCommand
import ru.cherepokivan.irremote.domain.model.IRProtocol
import com.google.gson.Gson

/**
 * Маппер для преобразования IRDB DTO в domain модели
 */
object IRDBMapper {

    private val gson = Gson()

    fun toDomain(dto: IRDBDeviceDto): Device {
        return Device(
            id = 0, // будет назначен Room
            name = "${dto.brand} ${dto.model}",
            type = mapDeviceType(dto.type),
            brand = dto.brand,
            model = dto.model,
            isFavorite = false
        )
    }

    fun commandsToDomain(
        deviceId: Long,
        commands: Map<String, IRDBCommandDto>,
        defaultProtocol: String
    ): List<IRCommand> {
        return commands.map { (name, commandDto) ->
            IRCommand(
                id = 0, // будет назначен Room
                deviceId = deviceId,
                name = name,
                protocol = mapProtocol(commandDto.protocol ?: defaultProtocol),
                frequency = commandDto.frequency ?: 38000,
                pattern = commandDto.raw ?: parsePattern(commandDto)
            )
        }
    }

    private fun parsePattern(dto: IRDBCommandDto): List<Int> {
        // Попытка извлечь паттерн из различных форматов
        return when {
            dto.raw != null -> dto.raw
            dto.data != null -> parseHexData(dto.data)
            dto.command != null && dto.address != null -> {
                // Генерация паттерна из адреса и команды (упрощённо)
                generateNECPattern(dto.address, dto.command)
            }
            else -> emptyList()
        }
    }

    private fun parseHexData(hex: String): List<Int> {
        return try {
            hex.replace("0x", "")
                .chunked(2)
                .map { it.toInt(16) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun generateNECPattern(address: String, command: String): List<Int> {
        // Упрощённая генерация NEC паттерна
        // В реальности нужна более сложная логика
        val addr = address.removePrefix("0x").toIntOrNull(16) ?: 0
        val cmd = command.removePrefix("0x").toIntOrNull(16) ?: 0

        return listOf(
            9000, 4500, // Lead
            560, 560,   // Address bits (упрощённо)
            560, 1690,  // Command bits (упрощённо)
            560         // Stop bit
        )
    }

    private fun mapDeviceType(type: String): DeviceType {
        return when (type.lowercase()) {
            "tv", "television" -> DeviceType.TV
            "ac", "air conditioner", "airconditioner" -> DeviceType.AC
            "fan" -> DeviceType.FAN
            "projector" -> DeviceType.PROJECTOR
            "audio", "amplifier", "receiver" -> DeviceType.AUDIO
            "stb", "set-top-box", "settopbox" -> DeviceType.SET_TOP_BOX
            "dvd", "blu-ray", "bluray" -> DeviceType.DVD_PLAYER
            else -> DeviceType.OTHER
        }
    }

    private fun mapProtocol(protocol: String): IRProtocol {
        return when (protocol.uppercase()) {
            "NEC" -> IRProtocol.NEC
            "RC5" -> IRProtocol.RC5
            "RC6" -> IRProtocol.RC6
            "SAMSUNG" -> IRProtocol.SAMSUNG
            "LG" -> IRProtocol.LG
            "SONY" -> IRProtocol.SONY
            "PANASONIC" -> IRProtocol.PANASONIC
            "JVC" -> IRProtocol.JVC
            "SHARP" -> IRProtocol.SHARP
            "DENON" -> IRProtocol.DENON
            else -> IRProtocol.RAW
        }
    }
}
