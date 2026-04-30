package com.irremote.app.presentation.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irremote.app.domain.usecase.CheckIRAvailabilityUseCase
import com.irremote.app.domain.usecase.GetCarrierFrequenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val checkIRAvailabilityUseCase: CheckIRAvailabilityUseCase,
    private val getCarrierFrequenciesUseCase: GetCarrierFrequenciesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        loadIRInfo()
    }

    private fun loadIRInfo() {
        viewModelScope.launch {
            val isAvailable = checkIRAvailabilityUseCase()
            val frequencies = getCarrierFrequenciesUseCase()

            _uiState.value = _uiState.value.copy(
                isIRAvailable = isAvailable,
                carrierFrequencies = frequencies?.toList() ?: emptyList()
            )
        }
    }
}

data class SettingsUiState(
    val isIRAvailable: Boolean = false,
    val carrierFrequencies: List<Int> = emptyList()
)
