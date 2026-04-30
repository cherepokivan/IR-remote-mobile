package com.irremote.app.data.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.irremote.app.data.local.entity.IRCommandEntity
import com.irremote.app.domain.model.IRCommand
import com.irremote.app.domain.model.IRProtocol

private val gson = Gson()

fun IRCommandEntity.toDomain(): IRCommand {
    val patternType = object : TypeToken<List<Int>>() {}.type
    val patternList = gson.fromJson<List<Int>>(pattern, patternType)

    return IRCommand(
        id = id,
        deviceId = deviceId,
        name = name,
        protocol = IRProtocol.fromString(protocol),
        frequency = frequency,
        pattern = patternList,
        createdAt = createdAt
    )
}

fun IRCommand.toEntity(): IRCommandEntity {
    return IRCommandEntity(
        id = id,
        deviceId = deviceId,
        name = name,
        protocol = protocol.name,
        frequency = frequency,
        pattern = gson.toJson(pattern),
        createdAt = createdAt
    )
}
