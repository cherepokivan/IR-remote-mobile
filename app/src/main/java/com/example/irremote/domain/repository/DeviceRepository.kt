package com.example.irremote.domain.repository

import com.example.irremote.domain.model.Device
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {
    fun getAllDevices(): Flow<List<Device>>
    suspend fun getDeviceById(id: Long): Device?
    fun getFavoriteDevices(): Flow<List<Device>>
    fun searchDevices(query: String): Flow<List<Device>>
    suspend fun addDevice(device: Device): Long
    suspend fun updateDevice(device: Device)
    suspend fun deleteDevice(device: Device)
    suspend fun toggleFavorite(deviceId: Long)
}
