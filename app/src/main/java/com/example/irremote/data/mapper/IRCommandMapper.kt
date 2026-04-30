package com.example.irremote.data.mapper

import com.example.irremote.data.local.entity.IRCommandEntity
import com.example.irremote.domain.model.IRCommand
import com.example.irremote.domain.model.IRProtocol
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
            protocol = IRProtocol.valueOf(entity.protocol),
            frequency = entity.frequency,
            pattern = gson.fromJson(entity.pattern, Array<Int>::class.java).toList()
        )
    }

    fun toDomainList(entities: List<IRCommandEntity>): List<IRCommand> {
        return entities.map { toDomain(it) }
    }
}
