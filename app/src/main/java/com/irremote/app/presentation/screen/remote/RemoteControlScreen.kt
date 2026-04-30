package com.irremote.app.presentation.screen.remote

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.irremote.app.domain.model.IRCommand

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemoteControlScreen(
    onNavigateBack: () -> Unit,
    viewModel: RemoteControlViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.device?.name ?: "Пульт управления") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                uiState.device == null -> {
                    Text(
                        text = "Устройство не найдено",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                uiState.commands.isEmpty() -> {
                    Text(
                        text = "Нет доступных команд для этого устройства",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(32.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.commands) { command ->
                            RemoteButton(
                                command = command,
                                isTransmitting = uiState.transmittingCommandId == command.id,
                                wasTransmitted = uiState.lastTransmittedCommandId == command.id,
                                onClick = { viewModel.transmitCommand(command) }
                            )
                        }
                    }
                }
            }

            // Показываем ошибку если есть
            uiState.error?.let { error ->
                Snackbar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    action = {
                        TextButton(onClick = { viewModel.clearError() }) {
                            Text("OK")
                        }
                    }
                ) {
                    Text(error)
                }
            }
        }
    }
}

@Composable
fun RemoteButton(
    command: IRCommand,
    isTransmitting: Boolean,
    wasTransmitted: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxWidth(),
        enabled = !isTransmitting,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (wasTransmitted) {
                MaterialTheme.colorScheme.tertiary
            } else {
                MaterialTheme.colorScheme.primary
            }
        )
    ) {
        if (isTransmitting) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            Text(
                text = formatCommandName(command.name),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

private fun formatCommandName(name: String): String {
    return when (name.uppercase()) {
        "POWER" -> "⏻"
        "VOL_UP", "VOLUME_UP" -> "VOL +"
        "VOL_DOWN", "VOLUME_DOWN" -> "VOL -"
        "CH_UP", "CHANNEL_UP" -> "CH +"
        "CH_DOWN", "CHANNEL_DOWN" -> "CH -"
        "MUTE" -> "🔇"
        "MENU" -> "☰"
        "OK" -> "OK"
        "UP" -> "▲"
        "DOWN" -> "▼"
        "LEFT" -> "◄"
        "RIGHT" -> "►"
        "BACK" -> "←"
        "HOME" -> "⌂"
        else -> name.replace("_", " ")
    }
}
