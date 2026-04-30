package ru.cherepokivan.irremote.presentation.screen.adddevice

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cherepokivan.irremote.domain.model.DeviceType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDeviceScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddDeviceViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var showTypeDropdown by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Добавить устройство") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Назад")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = state.name,
                onValueChange = { viewModel.onNameChange(it) },
                label = { Text("Название") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            ExposedDropdownMenuBox(
                expanded = showTypeDropdown,
                onExpandedChange = { showTypeDropdown = it }
            ) {
                OutlinedTextField(
                    value = getDeviceTypeName(state.type),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Тип устройства") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showTypeDropdown) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = showTypeDropdown,
                    onDismissRequest = { showTypeDropdown = false }
                ) {
                    DeviceType.values().forEach { type ->
                        DropdownMenuItem(
                            text = { Text(getDeviceTypeName(type)) },
                            onClick = {
                                viewModel.onTypeChange(type)
                                showTypeDropdown = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = state.brand,
                onValueChange = { viewModel.onBrandChange(it) },
                label = { Text("Бренд") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = state.model,
                onValueChange = { viewModel.onModelChange(it) },
                label = { Text("Модель") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            state.errorMessage?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(
                onClick = { viewModel.saveDevice(onNavigateBack) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Сохранить")
            }
        }
    }
}

@Composable
private fun getDeviceTypeName(type: DeviceType): String = when (type) {
    DeviceType.TV -> "Телевизор"
    DeviceType.AC -> "Кондиционер"
    DeviceType.FAN -> "Вентилятор"
    DeviceType.PROJECTOR -> "Проектор"
    DeviceType.AUDIO -> "Аудиосистема"
    DeviceType.SET_TOP_BOX -> "ТВ-приставка"
    DeviceType.DVD_PLAYER -> "DVD-плеер"
    DeviceType.OTHER -> "Другое"
}
