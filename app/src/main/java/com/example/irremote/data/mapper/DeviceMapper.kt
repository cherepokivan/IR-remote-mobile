package com.example.irremote.data.mapper

import com.example.irremote.data.local.entity.DeviceEntity
import com.example.irremote.domain.model.Device
import com.example.irremote.domain.model.DeviceType

object DeviceMapper {

    fun toEntity(device: Device): DeviceEntity {
        return DeviceEntity(
            id = device.id,
            name = device.name,
            type = device.type.name,
            brand = device.brand,
            model = device.model,
            isFavorite = device.isFavorite
        )
    }

    fun toDomain(entity: DeviceEntity): Device {
        return Device(
            id = entity.id,
            name = entity.name,
            type = DeviceType.valueOf(entity.type),
            brand = entity.brand,
            model = entity.model,
            isFavorite = entity.isFavorite
        )
    }

    fun toDomainList(entities: List<DeviceEntity>): List<Device> {
        return entities.map { toDomain(it) }
    }
}
