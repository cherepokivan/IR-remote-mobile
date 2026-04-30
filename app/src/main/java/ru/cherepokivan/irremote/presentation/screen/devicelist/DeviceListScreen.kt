package ru.cherepokivan.irremote.presentation.screen.devicelist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.cherepokivan.irremote.domain.model.DeviceType
import ru.cherepokivan.irremote.presentation.components.DeviceCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceListScreen(
    onNavigateToRemote: (Long) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: DeviceListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Все устройства") },
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
        ) {
            // Поиск
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Поиск устройств...") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                trailingIcon = {
                    if (state.searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.onSearchQueryChange("") }) {
                            Icon(Icons.Default.Clear, "Очистить")
                        }
                    }
                },
                singleLine = true
            )

            // Фильтры по типу
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = state.selectedType == null,
                        onClick = { viewModel.onTypeFilterChange(null) },
                        label = { Text("Все") }
                    )
                }

                items(DeviceType.values()) { type ->
                    FilterChip(
                        selected = state.selectedType == type,
                        onClick = { viewModel.onTypeFilterChange(type) },
                        label = { Text(getDeviceTypeName(type)) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Список устройств
            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (state.filteredDevices.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            Icons.Default.SearchOff,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Устройства не найдены",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.filteredDevices) { device ->
                        DeviceCard(
                            device = device,
                            onClick = { onNavigateToRemote(device.id) },
                            onFavoriteClick = { viewModel.toggleFavorite(device.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun getDeviceTypeName(type: DeviceType): String = when (type) {
    DeviceType.TV -> "ТВ"
    DeviceType.AC -> "AC"
    DeviceType.FAN -> "Вентилятор"
    DeviceType.PROJECTOR -> "Проектор"
    DeviceType.AUDIO -> "Аудио"
    DeviceType.SET_TOP_BOX -> "Приставка"
    DeviceType.DVD_PLAYER -> "DVD"
    DeviceType.OTHER -> "Другое"
}
