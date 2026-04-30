package com.example.irremote.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.irremote.domain.model.Device
import com.example.irremote.domain.usecase.DeviceUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val deviceUseCases: DeviceUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        loadDevices()
    }

    private fun loadDevices() {
        viewModelScope.launch {
            deviceUseCases.getAllDevices().collect { devices ->
                _state.value = _state.value.copy(
                    allDevices = devices,
                    isLoading = false
                )
            }
        }

        viewModelScope.launch {
            deviceUseCases.getFavoriteDevices().collect { favorites ->
                _state.value = _state.value.copy(favoriteDevices = favorites)
            }
        }
    }

    fun toggleFavorite(deviceId: Long) {
        viewModelScope.launch {
            deviceUseCases.toggleFavorite(deviceId)
        }
    }
}

data class HomeState(
    val allDevices: List<Device> = emptyList(),
    val favoriteDevices: List<Device> = emptyList(),
    val isLoading: Boolean = true
)
