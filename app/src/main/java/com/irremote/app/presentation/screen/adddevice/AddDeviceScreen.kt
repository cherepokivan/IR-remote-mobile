package com.irremote.app.presentation.screen.adddevice

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.irremote.app.domain.model.DeviceType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDeviceScreen(
    onNavigateBack: () -> Unit,
    onDeviceAdded: (Long) -> Unit,
    viewModel: AddDeviceViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showTypeDropdown by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Добавить устройство") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Название устройства
            OutlinedTextField(
                value = uiState.name,
                onValueChange = { viewModel.updateName(it) },
                label = { Text("Название устройства") },
                placeholder = { Text("Например: Телевизор в гостиной") },
                isError = uiState.nameError != null,
                supportingText = uiState.nameError?.let { { Text(it) } },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Тип устройства
            ExposedDropdownMenuBox(
                expanded = showTypeDropdown,
                onExpandedChange = { showTypeDropdown = it }
            ) {
                OutlinedTextField(
                    value = uiState.type?.let { getDeviceTypeName(it) } ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Тип устройства") },
                    placeholder = { Text("Выберите тип") },
                    trailingIcon = {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    },
                    isError = uiState.typeError != null,
                    supportingText = uiState.typeError?.let { { Text(it) } },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = showTypeDropdown,
                    onDismissRequest = { showTypeDropdown = false }
                ) {
                    DeviceType.entries.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(getDeviceTypeName(type)) },
                            onClick = {
                                viewModel.updateType(type)
                                showTypeDropdown = false
                            }
                        )
                    }
                }
            }

            // Бренд
            OutlinedTextField(
                value = uiState.brand,
                onValueChange = { viewModel.updateBrand(it) },
                label = { Text("Бренд") },
                placeholder = { Text("Например: Samsung") },
                isError = uiState.brandError != null,
                supportingText = uiState.brandError?.let { { Text(it) } },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Модель (опционально)
            OutlinedTextField(
                value = uiState.model,
                onValueChange = { viewModel.updateModel(it) },
                label = { Text("Модель (необязательно)") },
                placeholder = { Text("Например: UE55TU7100") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.weight(1f))

            // Кнопка сохранения
            Button(
                onClick = { viewModel.saveDevice(onDeviceAdded) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isSaving
            ) {
                if (uiState.isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Сохранить")
                }
            }

            // Показываем ошибку если есть
            uiState.error?.let { error ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.weight(1f)
                        )
                        TextButton(onClick = { viewModel.clearError() }) {
                            Text("OK")
                        }
                    }
                }
            }
        }
    }
}

private fun getDeviceTypeName(type: DeviceType): String {
    return when (type) {
        DeviceType.TV -> "Телевизор"
        DeviceType.AC -> "Кондиционер"
        DeviceType.FAN -> "Вентилятор"
        DeviceType.PROJECTOR -> "Проектор"
        DeviceType.AUDIO -> "Аудиосистема"
        DeviceType.SET_TOP_BOX -> "Приставка"
        DeviceType.DVD_PLAYER -> "DVD/Blu-ray плеер"
        DeviceType.OTHER -> "Другое"
    }
}
