package com.example.irremote.presentation.screen.home

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
import com.example.irremote.presentation.components.DeviceCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToRemote: (Long) -> Unit,
    onNavigateToAddDevice: () -> Unit,
    onNavigateToDeviceList: () -> Unit,
    onNavigateToSettings: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("IR Remote") },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, "Настройки")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAddDevice) {
                Icon(Icons.Default.Add, "Добавить устройство")
            }
        }
    ) { padding ->
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Избранные устройства
                if (state.favoriteDevices.isNotEmpty()) {
                    item {
                        Text(
                            text = "Избранное",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    item {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(state.favoriteDevices) { device ->
                                DeviceCard(
                                    device = device,
                                    onClick = { onNavigateToRemote(device.id) },
                                    onFavoriteClick = { viewModel.toggleFavorite(device.id) },
                                    modifier = Modifier.width(160.dp)
                                )
                            }
                        }
                    }
                }

                // Все устройства
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Все устройства",
                            style = MaterialTheme.typography.titleLarge
                        )
                        TextButton(onClick = onNavigateToDeviceList) {
                            Text("Показать все")
                            Icon(
                                Icons.Default.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }

                if (state.allDevices.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    Icons.Default.DevicesOther,
                                    contentDescription = null,
                                    modifier = Modifier.size(64.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = "Нет устройств",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "Добавьте первое устройство",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                } else {
                    items(state.allDevices.take(5)) { device ->
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
