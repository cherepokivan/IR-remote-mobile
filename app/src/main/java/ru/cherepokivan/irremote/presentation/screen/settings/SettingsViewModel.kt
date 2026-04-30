package ru.cherepokivan.irremote.presentation.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.cherepokivan.irremote.data.repository.ImportProgress
import ru.cherepokivan.irremote.domain.usecase.IRDBUseCases
import ru.cherepokivan.irremote.domain.usecase.IRTransmitterUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val irTransmitterUseCases: IRTransmitterUseCases,
    private val irdbUseCases: IRDBUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    init {
        checkIRAvailability()
    }

    private fun checkIRAvailability() {
        viewModelScope.launch {
            try {
                val isAvailable = irTransmitterUseCases.checkIRAvailability()
                val frequencies = if (isAvailable) {
                    irTransmitterUseCases.getCarrierFrequencies()
                } else {
                    emptyList()
                }

                _state.value = _state.value.copy(
                    isIRAvailable = isAvailable,
                    carrierFrequencies = frequencies
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isIRAvailable = false,
                    carrierFrequencies = emptyList()
                )
            }
        }
    }

    fun importIRDB() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isImporting = true,
                importProgress = 0,
                importMessage = "Подготовка..."
            )

            irdbUseCases.importIRDB().collect { progress ->
                when (progress) {
                    is ImportProgress.Loading -> {
                        _state.value = _state.value.copy(
                            importProgress = progress.progress,
                            importMessage = progress.message
                        )
                    }
                    is ImportProgress.Success -> {
                        _state.value = _state.value.copy(
                            isImporting = false,
                            importProgress = 100,
                            importMessage = "Успешно импортировано ${progress.importedCount} устройств",
                            showImportSuccess = true
                        )
                    }
                    is ImportProgress.Error -> {
                        _state.value = _state.value.copy(
                            isImporting = false,
                            importProgress = 0,
                            importMessage = "Ошибка: ${progress.message}",
                            showImportError = true
                        )
                    }
                }
            }
        }
    }

    fun importFlipperIRDB() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isImporting = true,
                importProgress = 0,
                importMessage = "Подготовка..."
            )

            irdbUseCases.importFlipperIRDB().collect { progress ->
                when (progress) {
                    is ImportProgress.Loading -> {
                        _state.value = _state.value.copy(
                            importProgress = progress.progress,
                            importMessage = progress.message
                        )
                    }
                    is ImportProgress.Success -> {
                        _state.value = _state.value.copy(
                            isImporting = false,
                            importProgress = 100,
                            importMessage = "Успешно импортировано ${progress.importedCount} устройств",
                            showImportSuccess = true
                        )
                    }
                    is ImportProgress.Error -> {
                        _state.value = _state.value.copy(
                            isImporting = false,
                            importProgress = 0,
                            importMessage = "Ошибка: ${progress.message}",
                            showImportError = true
                        )
                    }
                }
            }
        }
    }

    fun dismissImportDialog() {
        _state.value = _state.value.copy(
            showImportSuccess = false,
            showImportError = false
        )
    }
}

data class SettingsState(
    val isIRAvailable: Boolean = false,
    val carrierFrequencies: List<Int> = emptyList(),
    val isImporting: Boolean = false,
    val importProgress: Int = 0,
    val importMessage: String = "",
    val showImportSuccess: Boolean = false,
    val showImportError: Boolean = false
)
