package com.example.irremote.presentation.screen.remote

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.irremote.domain.model.Device
import com.example.irremote.domain.model.IRCommand
import com.example.irremote.domain.usecase.DeviceUseCases
import com.example.irremote.domain.usecase.IRCommandUseCases
import com.example.irremote.domain.usecase.IRTransmitterUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemoteControlViewModel @Inject constructor(
    private val deviceUseCases: DeviceUseCases,
    private val commandUseCases: IRCommandUseCases,
    private val transmitterUseCases: IRTransmitterUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val deviceId: Long = savedStateHandle.get<Long>("deviceId") ?: 0L

    private val _state = MutableStateFlow(RemoteControlState())
    val state: StateFlow<RemoteControlState> = _state.asStateFlow()

    init {
        loadDevice()
        loadCommands()
    }

    private fun loadDevice() {
        viewModelScope.launch {
            val device = deviceUseCases.getDeviceById(deviceId)
            _state.value = _state.value.copy(device = device)
        }
    }

    private fun loadCommands() {
        viewModelScope.launch {
            commandUseCases.getCommandsByDeviceId(deviceId).collect { commands ->
                _state.value = _state.value.copy(
                    commands = commands,
                    isLoading = false
                )
            }
        }
    }

    fun transmitCommand(command: IRCommand) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                transmittingCommandId = command.id,
                errorMessage = null
            )

            val result = transmitterUseCases.transmitIRCommand(command)

            result.onSuccess {
                _state.value = _state.value.copy(
                    transmittingCommandId = null,
                    lastTransmittedCommandId = command.id
                )
            }.onFailure { error ->
                _state.value = _state.value.copy(
                    transmittingCommandId = null,
                    errorMessage = error.message ?: "Ошибка передачи"
                )
            }
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(errorMessage = null)
    }
}

data class RemoteControlState(
    val device: Device? = null,
    val commands: List<IRCommand> = emptyList(),
    val isLoading: Boolean = true,
    val transmittingCommandId: Long? = null,
    val lastTransmittedCommandId: Long? = null,
    val errorMessage: String? = null
)
