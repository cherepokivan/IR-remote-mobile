package com.irremote.app.data.repository

import com.irremote.app.data.local.dao.DeviceDao
import com.irremote.app.data.mapper.toDomain
import com.irremote.app.data.mapper.toEntity
import com.irremote.app.domain.model.Device
import com.irremote.app.domain.repository.DeviceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(
    private val deviceDao: DeviceDao
) : DeviceRepository {

    override fun getAllDevices(): Flow<List<Device>> {
        return deviceDao.getAllDevices().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getDeviceById(deviceId: Long): Device? {
        return deviceDao.getDeviceById(deviceId)?.toDomain()
    }

    override fun getDevicesByType(type: String): Flow<List<Device>> {
        return deviceDao.getDevicesByType(type).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getFavoriteDevices(): Flow<List<Device>> {
        return deviceDao.getFavoriteDevices().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun insertDevice(device: Device): Long {
        return deviceDao.insertDevice(device.toEntity())
    }

    override suspend fun updateDevice(device: Device) {
        deviceDao.updateDevice(device.toEntity())
    }

    override suspend fun deleteDevice(device: Device) {
        deviceDao.deleteDevice(device.toEntity())
    }

    override suspend fun updateFavoriteStatus(deviceId: Long, isFavorite: Boolean) {
        deviceDao.updateFavoriteStatus(deviceId, isFavorite)
    }

    override suspend fun deleteAllDevices() {
        deviceDao.deleteAllDevices()
    }
}
