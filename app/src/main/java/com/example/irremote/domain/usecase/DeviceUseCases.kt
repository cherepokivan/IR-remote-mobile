package com.example.irremote.domain.usecase

import com.example.irremote.domain.model.Device
import com.example.irremote.domain.repository.DeviceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class DeviceUseCases(
    val getAllDevices: GetAllDevicesUseCase,
    val getDeviceById: GetDeviceByIdUseCase,
    val addDevice: AddDeviceUseCase,
    val updateDevice: UpdateDeviceUseCase,
    val deleteDevice: DeleteDeviceUseCase,
    val getFavoriteDevices: GetFavoriteDevicesUseCase,
    val toggleFavorite: ToggleFavoriteUseCase,
    val searchDevices: SearchDevicesUseCase
)

class GetAllDevicesUseCase @Inject constructor(
    private val repository: DeviceRepository
) {
    operator fun invoke(): Flow<List<Device>> = repository.getAllDevices()
}

class GetDeviceByIdUseCase @Inject constructor(
    private val repository: DeviceRepository
) {
    suspend operator fun invoke(id: Long): Device? = repository.getDeviceById(id)
}

class AddDeviceUseCase @Inject constructor(
    private val repository: DeviceRepository
) {
    suspend operator fun invoke(device: Device): Long = repository.addDevice(device)
}

class UpdateDeviceUseCase @Inject constructor(
    private val repository: DeviceRepository
) {
    suspend operator fun invoke(device: Device) = repository.updateDevice(device)
}

class DeleteDeviceUseCase @Inject constructor(
    private val repository: DeviceRepository
) {
    suspend operator fun invoke(device: Device) = repository.deleteDevice(device)
}

class GetFavoriteDevicesUseCase @Inject constructor(
    private val repository: DeviceRepository
) {
    operator fun invoke(): Flow<List<Device>> = repository.getFavoriteDevices()
}

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: DeviceRepository
) {
    suspend operator fun invoke(deviceId: Long) = repository.toggleFavorite(deviceId)
}

class SearchDevicesUseCase @Inject constructor(
    private val repository: DeviceRepository
) {
    operator fun invoke(query: String): Flow<List<Device>> = repository.searchDevices(query)
}
