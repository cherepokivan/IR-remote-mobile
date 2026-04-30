package com.irremote.app.presentation.screen.adddevice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irremote.app.domain.model.Device
import com.irremote.app.domain.model.DeviceType
import com.irremote.app.domain.usecase.AddDeviceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddDeviceViewModel @Inject constructor(
    private val addDeviceUseCase: AddDeviceUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddDeviceUiState())
    val uiState: StateFlow<AddDeviceUiState> = _uiState.asStateFlow()

    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(
            name = name,
            nameError = null
        )
    }

    fun updateType(type: DeviceType) {
        _uiState.value = _uiState.value.copy(
            type = type,
            typeError = null
        )
    }

    fun updateBrand(brand: String) {
        _uiState.value = _uiState.value.copy(
            brand = brand,
            brandError = null
        )
    }

    fun updateModel(model: String) {
        _uiState.value = _uiState.value.copy(model = model)
    }

    fun saveDevice(onSuccess: (Long) -> Unit) {
        if (!validateForm()) {
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSaving = true)

            try {
                val device = Device(
                    name = _uiState.value.name.trim(),
                    type = _uiState.value.type!!,
                    brand = _uiState.value.brand.trim(),
                    model = _uiState.value.model.trim().takeIf { it.isNotEmpty() }
                )

                val deviceId = addDeviceUseCase(device)
                onSuccess(deviceId)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    error = e.message ?: "Ошибка сохранения устройства"
                )
            }
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        if (_uiState.value.name.isBlank()) {
            _uiState.value = _uiState.value.copy(nameError = "Введите название устройства")
            isValid = false
        }

        if (_uiState.value.type == null) {
            _uiState.value = _uiState.value.copy(typeError = "Выберите тип устройства")
            isValid = false
        }

        if (_uiState.value.brand.isBlank()) {
            _uiState.value = _uiState.value.copy(brandError = "Введите бренд устройства")
            isValid = false
        }

        return isValid
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class AddDeviceUiState(
    val name: String = "",
    val type: DeviceType? = null,
    val brand: String = "",
    val model: String = "",
    val nameError: String? = null,
    val typeError: String? = null,
    val brandError: String? = null,
    val isSaving: Boolean = false,
    val error: String? = null
)
