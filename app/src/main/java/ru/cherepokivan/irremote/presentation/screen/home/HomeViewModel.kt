package ru.cherepokivan.irremote.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.cherepokivan.irremote.domain.model.Device
import ru.cherepokivan.irremote.domain.usecase.DeviceUseCases
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
            try {
                deviceUseCases.getAllDevices().collect { devices ->
                    _state.value = _state.value.copy(
                        allDevices = devices,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false)
            }
        }

        viewModelScope.launch {
            try {
                deviceUseCases.getFavoriteDevices().collect { favorites ->
                    _state.value = _state.value.copy(favoriteDevices = favorites)
                }
            } catch (e: Exception) {
                // Игнорируем ошибку, показываем пустой список
            }
        }
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
}

data class HomeState(
    val allDevices: List<Device> = emptyList(),
    val favoriteDevices: List<Device> = emptyList(),
    val isLoading: Boolean = true
)
