package com.irremote.app.presentation.screen.remote

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irremote.app.domain.model.Device
import com.irremote.app.domain.model.IRCommand
import com.irremote.app.domain.usecase.GetCommandsByDeviceIdUseCase
import com.irremote.app.domain.usecase.GetDeviceByIdUseCase
import com.irremote.app.domain.usecase.TransmitIRCommandUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemoteControlViewModel @Inject constructor(
    private val getDeviceByIdUseCase: GetDeviceByIdUseCase,
    private val getCommandsByDeviceIdUseCase: GetCommandsByDeviceIdUseCase,
    private val transmitIRCommandUseCase: TransmitIRCommandUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val deviceId: Long = savedStateHandle.get<String>("deviceId")?.toLongOrNull() ?: 0L

    private val _uiState = MutableStateFlow(RemoteControlUiState())
    val uiState: StateFlow<RemoteControlUiState> = _uiState.asStateFlow()

    init {
        loadDevice()
        loadCommands()
    }

    private fun loadDevice() {
        viewModelScope.launch {
            val device = getDeviceByIdUseCase(deviceId)
            _uiState.value = _uiState.value.copy(
                device = device,
                isLoading = false
            )
        }
    }

    private fun loadCommands() {
        viewModelScope.launch {
            getCommandsByDeviceIdUseCase(deviceId).collect { commands ->
                _uiState.value = _uiState.value.copy(
                    commands = commands,
                    isLoading = false
                )
            }
        }
    }

    fun transmitCommand(command: IRCommand) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                transmittingCommandId = command.id,
                error = null
            )

            val result = transmitIRCommandUseCase(command)

            result.fold(
                onSuccess = {
                    _uiState.value = _uiState.value.copy(
                        transmittingCommandId = null,
                        lastTransmittedCommandId = command.id
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        transmittingCommandId = null,
                        error = error.message ?: "Ошибка передачи ИК-сигнала"
                    )
                }
            )
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class RemoteControlUiState(
    val device: Device? = null,
    val commands: List<IRCommand> = emptyList(),
    val isLoading: Boolean = true,
    val transmittingCommandId: Long? = null,
    val lastTransmittedCommandId: Long? = null,
    val error: String? = null
)
