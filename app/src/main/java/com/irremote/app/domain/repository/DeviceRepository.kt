package com.irremote.app.domain.repository

import com.irremote.app.domain.model.Device
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {
    fun getAllDevices(): Flow<List<Device>>

    suspend fun getDeviceById(deviceId: Long): Device?

    fun getDevicesByType(type: String): Flow<List<Device>>

    fun getFavoriteDevices(): Flow<List<Device>>

    suspend fun insertDevice(device: Device): Long

    suspend fun updateDevice(device: Device)

    suspend fun deleteDevice(device: Device)

    suspend fun updateFavoriteStatus(deviceId: Long, isFavorite: Boolean)

    suspend fun deleteAllDevices()
}
