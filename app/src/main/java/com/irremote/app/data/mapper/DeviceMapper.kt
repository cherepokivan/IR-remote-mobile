package com.irremote.app.data.mapper

import com.irremote.app.data.local.entity.DeviceEntity
import com.irremote.app.domain.model.Device
import com.irremote.app.domain.model.DeviceType

fun DeviceEntity.toDomain(): Device {
    return Device(
        id = id,
        name = name,
        type = DeviceType.fromString(type),
        brand = brand,
        model = model,
        isFavorite = isFavorite,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Device.toEntity(): DeviceEntity {
    return DeviceEntity(
        id = id,
        name = name,
        type = type.name,
        brand = brand,
        model = model,
        isFavorite = isFavorite,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
