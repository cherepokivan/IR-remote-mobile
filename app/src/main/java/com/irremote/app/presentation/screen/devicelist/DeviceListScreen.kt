package com.irremote.app.presentation.screen.devicelist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.irremote.app.domain.model.Device
import com.irremote.app.domain.model.DeviceType
import com.irremote.app.presentation.components.DeviceCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceListScreen(
    onNavigateBack: () -> Unit,
    onNavigateToDevice: (Long) -> Unit,
    viewModel: DeviceListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showSearchBar by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            if (showSearchBar) {
                SearchBar(
                    query = uiState.searchQuery,
                    onQueryChange = { viewModel.updateSearchQuery(it) },
                    onSearch = { },
                    active = true,
                    onActiveChange = { if (!it) showSearchBar = false },
                    placeholder = { Text("Поиск устройств...") },
                    leadingIcon = {
                        IconButton(onClick = { showSearchBar = false }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                        }
                    }
                ) {}
            } else {
                TopAppBar(
                    title = { Text("Все устройства") },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                        }
                    },
                    actions = {
                        IconButton(onClick = { showSearchBar = true }) {
                            Icon(Icons.Default.Search, contentDescription = "Поиск")
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Фильтр по типу
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        selected = uiState.selectedType == null,
                        onClick = { viewModel.selectType(null) },
                        label = { Text("Все") }
                    )
                }

                items(DeviceType.entries) { type ->
                    FilterChip(
                        selected = uiState.selectedType == type,
                        onClick = { viewModel.selectType(type) },
                        label = { Text(getDeviceTypeName(type)) }
                    )
                }
            }

            Divider()

            // Список устройств
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (uiState.devices.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (uiState.searchQuery.isNotEmpty()) {
                            "Устройства не найдены"
                        } else {
                            "Нет устройств"
                        },
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.devices, key = { it.id }) { device ->
                        DeviceCard(
                            device = device,
                            onClick = { onNavigateToDevice(device.id) },
                            onToggleFavorite = {
                                viewModel.toggleFavorite(device.id, !device.isFavorite)
                            }
                        )
                    }
                }
            }
        }
    }
}

private fun getDeviceTypeName(type: DeviceType): String {
    return when (type) {
        DeviceType.TV -> "ТВ"
        DeviceType.AC -> "Кондиционер"
        DeviceType.FAN -> "Вентилятор"
        DeviceType.PROJECTOR -> "Проектор"
        DeviceType.AUDIO -> "Аудио"
        DeviceType.SET_TOP_BOX -> "Приставка"
        DeviceType.DVD_PLAYER -> "DVD"
        DeviceType.OTHER -> "Другое"
    }
}
