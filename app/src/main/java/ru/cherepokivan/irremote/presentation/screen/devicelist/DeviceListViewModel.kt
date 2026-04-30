package ru.cherepokivan.irremote.presentation.screen.devicelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.cherepokivan.irremote.domain.model.Device
import ru.cherepokivan.irremote.domain.model.DeviceType
import ru.cherepokivan.irremote.domain.usecase.DeviceUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceListViewModel @Inject constructor(
    private val deviceUseCases: DeviceUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(DeviceListState())
    val state: StateFlow<DeviceListState> = _state.asStateFlow()

    init {
        loadDevices()
    }

    private fun loadDevices() {
        viewModelScope.launch {
            try {
                deviceUseCases.getAllDevices().collect { devices ->
                    _state.value = _state.value.copy(
                        allDevices = devices,
                        filteredDevices = filterDevices(devices, _state.value.searchQuery, _state.value.selectedType),
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _state.value = _state.value.copy(searchQuery = query)
        updateFilteredDevices()
    }

    fun onTypeFilterChange(type: DeviceType?) {
        _state.value = _state.value.copy(selectedType = type)
        updateFilteredDevices()
    }

    fun toggleFavorite(deviceId: Long) {
        viewModelScope.launch {
            try {
                deviceUseCases.toggleFavorite(deviceId)
            } catch (e: Exception) {
                // Игнорируем ошибку
            }
        }
    }

    private fun updateFilteredDevices() {
        val state = _state.value
        _state.value = state.copy(
            filteredDevices = filterDevices(state.allDevices, state.searchQuery, state.selectedType)
        )
    }

    private fun filterDevices(devices: List<Device>, query: String, type: DeviceType?): List<Device> {
        return devices.filter { device ->
            val matchesQuery = query.isBlank() ||
                    device.name.contains(query, ignoreCase = true) ||
                    device.brand.contains(query, ignoreCase = true) ||
                    device.model.contains(query, ignoreCase = true)

            val matchesType = type == null || device.type == type

            matchesQuery && matchesType
        }
    }
}

data class DeviceListState(
    val allDevices: List<Device> = emptyList(),
    val filteredDevices: List<Device> = emptyList(),
    val searchQuery: String = "",
    val selectedType: DeviceType? = null,
    val isLoading: Boolean = true
)
