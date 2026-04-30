package ru.cherepokivan.irremote.presentation.screen.adddevice

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
class AddDeviceViewModel @Inject constructor(
    private val deviceUseCases: DeviceUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(AddDeviceState())
    val state: StateFlow<AddDeviceState> = _state.asStateFlow()

    fun onNameChange(name: String) {
        _state.value = _state.value.copy(name = name)
    }

    fun onTypeChange(type: DeviceType) {
        _state.value = _state.value.copy(type = type)
    }

    fun onBrandChange(brand: String) {
        _state.value = _state.value.copy(brand = brand)
    }

    fun onModelChange(model: String) {
        _state.value = _state.value.copy(model = model)
    }

    fun saveDevice(onSuccess: () -> Unit) {
        val state = _state.value

        if (state.name.isBlank() || state.brand.isBlank() || state.model.isBlank()) {
            _state.value = state.copy(errorMessage = "Заполните все поля")
            return
        }

        viewModelScope.launch {
            try {
                val device = Device(
                    name = state.name,
                    type = state.type,
                    brand = state.brand,
                    model = state.model
                )

                deviceUseCases.addDevice(device)
                onSuccess()
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    errorMessage = "Ошибка сохранения: ${e.message}"
                )
            }
        }
    }
}

data class AddDeviceState(
    val name: String = "",
    val type: DeviceType = DeviceType.TV,
    val brand: String = "",
    val model: String = "",
    val errorMessage: String? = null
)
