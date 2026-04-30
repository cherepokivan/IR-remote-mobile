package ru.cherepokivan.irremote.data.repository

import ru.cherepokivan.irremote.data.local.dao.DeviceDao
import ru.cherepokivan.irremote.data.mapper.DeviceMapper
import ru.cherepokivan.irremote.domain.model.Device
import ru.cherepokivan.irremote.domain.repository.DeviceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceRepositoryImpl @Inject constructor(
    private val deviceDao: DeviceDao
) : DeviceRepository {

    override fun getAllDevices(): Flow<List<Device>> {
        return deviceDao.getAllDevices().map { entities ->
            DeviceMapper.toDomainList(entities)
        }
    }

    override suspend fun getDeviceById(id: Long): Device? {
        return deviceDao.getDeviceById(id)?.let { DeviceMapper.toDomain(it) }
    }

    override fun getFavoriteDevices(): Flow<List<Device>> {
        return deviceDao.getFavoriteDevices().map { entities ->
            DeviceMapper.toDomainList(entities)
        }
    }

    override fun searchDevices(query: String): Flow<List<Device>> {
        return deviceDao.searchDevices(query).map { entities ->
            DeviceMapper.toDomainList(entities)
        }
    }

    override suspend fun addDevice(device: Device): Long {
        return deviceDao.insert(DeviceMapper.toEntity(device))
    }

    override suspend fun updateDevice(device: Device) {
        deviceDao.update(DeviceMapper.toEntity(device))
    }

    override suspend fun deleteDevice(device: Device) {
        deviceDao.delete(DeviceMapper.toEntity(device))
    }

    override suspend fun toggleFavorite(deviceId: Long) {
        val device = deviceDao.getDeviceById(deviceId)
        device?.let {
            deviceDao.update(it.copy(isFavorite = !it.isFavorite))
        }
    }
}
