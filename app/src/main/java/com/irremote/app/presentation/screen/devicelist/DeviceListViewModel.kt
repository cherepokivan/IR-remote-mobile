package com.irremote.app.presentation.screen.devicelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irremote.app.domain.model.Device
import com.irremote.app.domain.model.DeviceType
import com.irremote.app.domain.usecase.DeleteDeviceUseCase
import com.irremote.app.domain.usecase.GetAllDevicesUseCase
import com.irremote.app.domain.usecase.GetDevicesByTypeUseCase
import com.irremote.app.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceListViewModel @Inject constructor(
    private val getAllDevicesUseCase: GetAllDevicesUseCase,
    private val getDevicesByTypeUseCase: GetDevicesByTypeUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val deleteDeviceUseCase: DeleteDeviceUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DeviceListUiState())
    val uiState: StateFlow<DeviceListUiState> = _uiState.asStateFlow()

    init {
        loadDevices()
    }

    private fun loadDevices() {
        viewModelScope.launch {
            val selectedType = _uiState.value.selectedType

            if (selectedType == null) {
                getAllDevicesUseCase().collect { devices ->
                    _uiState.value = _uiState.value.copy(
                        devices = filterDevices(devices),
                        isLoading = false
                    )
                }
            } else {
                getDevicesByTypeUseCase(selectedType.name).collect { devices ->
                    _uiState.value = _uiState.value.copy(
                        devices = filterDevices(devices),
                        isLoading = false
                    )
                }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        loadDevices()
    }

    fun selectType(type: DeviceType?) {
        _uiState.value = _uiState.value.copy(selectedType = type)
        loadDevices()
    }

    fun toggleFavorite(deviceId: Long, isFavorite: Boolean) {
        viewModelScope.launch {
            toggleFavoriteUseCase(deviceId, isFavorite)
        }
    }

    fun deleteDevice(device: Device) {
        viewModelScope.launch {
            deleteDeviceUseCase(device)
        }
    }

    private fun filterDevices(devices: List<Device>): List<Device> {
        val query = _uiState.value.searchQuery.lowercase()
        if (query.isEmpty()) return devices

        return devices.filter { device ->
            device.name.lowercase().contains(query) ||
            device.brand.lowercase().contains(query) ||
            device.model?.lowercase()?.contains(query) == true
        }
    }
}

data class DeviceListUiState(
    val devices: List<Device> = emptyList(),
    val searchQuery: String = "",
    val selectedType: DeviceType? = null,
    val isLoading: Boolean = true
)
