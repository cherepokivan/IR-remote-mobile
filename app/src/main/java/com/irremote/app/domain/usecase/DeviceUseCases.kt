package com.irremote.app.domain.usecase

import com.irremote.app.domain.model.Device
import com.irremote.app.domain.repository.DeviceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllDevicesUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
) {
    operator fun invoke(): Flow<List<Device>> {
        return deviceRepository.getAllDevices()
    }
}

class GetDeviceByIdUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
) {
    suspend operator fun invoke(deviceId: Long): Device? {
        return deviceRepository.getDeviceById(deviceId)
    }
}

class GetDevicesByTypeUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
) {
    operator fun invoke(type: String): Flow<List<Device>> {
        return deviceRepository.getDevicesByType(type)
    }
}

class GetFavoriteDevicesUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
) {
    operator fun invoke(): Flow<List<Device>> {
        return deviceRepository.getFavoriteDevices()
    }
}

class AddDeviceUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
) {
    suspend operator fun invoke(device: Device): Long {
        return deviceRepository.insertDevice(device)
    }
}

class UpdateDeviceUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
) {
    suspend operator fun invoke(device: Device) {
        deviceRepository.updateDevice(device)
    }
}

class DeleteDeviceUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
) {
    suspend operator fun invoke(device: Device) {
        deviceRepository.deleteDevice(device)
    }
}

class ToggleFavoriteUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
) {
    suspend operator fun invoke(deviceId: Long, isFavorite: Boolean) {
        deviceRepository.updateFavoriteStatus(deviceId, isFavorite)
    }
}
