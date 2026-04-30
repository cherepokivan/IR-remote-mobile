package com.irremote.app.presentation.screen.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Настройки") },
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
        ) {
            // Информация об ИК-порте
            SettingsSection(title = "ИК-передатчик") {
                SettingsItem(
                    title = "Статус",
                    subtitle = if (uiState.isIRAvailable) {
                        "Доступен"
                    } else {
                        "Недоступен"
                    },
                    icon = Icons.Default.Info
                )

                if (uiState.carrierFrequencies.isNotEmpty()) {
                    SettingsItem(
                        title = "Поддерживаемые частоты",
                        subtitle = "${uiState.carrierFrequencies.size} частот",
                        icon = Icons.Default.Info
                    )
                }
            }

            Divider()

            // О приложении
            SettingsSection(title = "О приложении") {
                SettingsItem(
                    title = "Версия",
                    subtitle = "1.0.0-alpha",
                    icon = Icons.Default.Info
                )

                SettingsItem(
                    title = "Разработчик",
                    subtitle = "IR Remote Team",
                    icon = Icons.Default.Info
                )
            }

            Divider()

            // Базы данных
            SettingsSection(title = "Базы данных ИК-сигналов") {
                SettingsItem(
                    title = "IRDB",
                    subtitle = "Основная база данных",
                    icon = Icons.Default.Info
                )

                SettingsItem(
                    title = "Flipper-IRDB",
                    subtitle = "Дополнительная база",
                    icon = Icons.Default.Info
                )

                SettingsItem(
                    title = "IRremoteESP8266",
                    subtitle = "База для кондиционеров",
                    icon = Icons.Default.Info
                )
            }
        }
    }
}

@Composable
fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        content()
    }
}

@Composable
fun SettingsItem(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: (() -> Unit)? = null
) {
    ListItem(
        headlineContent = { Text(title) },
        supportingContent = { Text(subtitle) },
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        modifier = if (onClick != null) {
            Modifier.padding(horizontal = 8.dp)
        } else {
            Modifier.padding(horizontal = 8.dp)
        }
    )
}
