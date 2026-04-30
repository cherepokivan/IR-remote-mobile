package com.irremote.app.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irremote.app.domain.model.Device
import com.irremote.app.domain.usecase.CheckIRAvailabilityUseCase
import com.irremote.app.domain.usecase.GetAllDevicesUseCase
import com.irremote.app.domain.usecase.GetFavoriteDevicesUseCase
import com.irremote.app.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllDevicesUseCase: GetAllDevicesUseCase,
    private val getFavoriteDevicesUseCase: GetFavoriteDevicesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val checkIRAvailabilityUseCase: CheckIRAvailabilityUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        checkIRAvailability()
        loadDevices()
    }

    private fun checkIRAvailability() {
        val isAvailable = checkIRAvailabilityUseCase()
        _uiState.value = _uiState.value.copy(isIRAvailable = isAvailable)
    }

    private fun loadDevices() {
        viewModelScope.launch {
            getAllDevicesUseCase().collect { devices ->
                _uiState.value = _uiState.value.copy(
                    allDevices = devices,
                    isLoading = false
                )
            }
        }

        viewModelScope.launch {
            getFavoriteDevicesUseCase().collect { favorites ->
                _uiState.value = _uiState.value.copy(favoriteDevices = favorites)
            }
        }
    }

    fun toggleFavorite(deviceId: Long, isFavorite: Boolean) {
        viewModelScope.launch {
            toggleFavoriteUseCase(deviceId, isFavorite)
        }
    }
}

data class HomeUiState(
    val isLoading: Boolean = true,
    val isIRAvailable: Boolean = false,
    val allDevices: List<Device> = emptyList(),
    val favoriteDevices: List<Device> = emptyList(),
    val error: String? = null
)
