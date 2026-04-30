package ru.cherepokivan.irremote.data.mapper

import ru.cherepokivan.irremote.data.local.entity.IRCommandEntity
import ru.cherepokivan.irremote.domain.model.IRCommand
import ru.cherepokivan.irremote.domain.model.IRProtocol
import com.google.gson.Gson

object IRCommandMapper {

    private val gson = Gson()

    fun toEntity(command: IRCommand): IRCommandEntity {
        return IRCommandEntity(
            id = command.id,
            deviceId = command.deviceId,
            name = command.name,
            protocol = command.protocol.name,
            frequency = command.frequency,
            pattern = gson.toJson(command.pattern)
        )
    }

    fun toDomain(entity: IRCommandEntity): IRCommand {
        return IRCommand(
            id = entity.id,
            deviceId = entity.deviceId,
            name = entity.name,
            protocol = try {
                IRProtocol.valueOf(entity.protocol)
            } catch (e: Exception) {
                IRProtocol.RAW
            },
            frequency = entity.frequency,
            pattern = try {
                gson.fromJson(entity.pattern, Array<Int>::class.java).toList()
            } catch (e: Exception) {
                emptyList()
            }
        )
    }

    fun toDomainList(entities: List<IRCommandEntity>): List<IRCommand> {
        return entities.map { toDomain(it) }
    }
}
