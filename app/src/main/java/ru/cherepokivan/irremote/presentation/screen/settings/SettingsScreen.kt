package ru.cherepokivan.irremote.presentation.screen.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var showImportDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Настройки") },
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
            // IR статус
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = if (state.isIRAvailable) Icons.Default.CheckCircle else Icons.Default.Warning,
                            contentDescription = null,
                            tint = if (state.isIRAvailable) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = "IR передатчик",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    Text(
                        text = if (state.isIRAvailable) "Доступен" else "Не обнаружен",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    if (state.isIRAvailable && state.carrierFrequencies.isNotEmpty()) {
                        Text(
                            text = "Поддерживаемые частоты: ${state.carrierFrequencies.joinToString(", ")} Hz",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Импорт баз данных
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Базы данных устройств",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "Загрузите полную базу IR кодов из интернета",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Button(
                        onClick = { showImportDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !state.isImporting
                    ) {
                        Icon(Icons.Default.Download, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Импортировать базу устройств")
                    }

                    if (state.isImporting) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            LinearProgressIndicator(
                                progress = state.importProgress / 100f,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text(
                                text = state.importMessage,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // О приложении
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "О приложении",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "IR Remote Control",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = "Версия 1.0.0",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = "Универсальный пульт дистанционного управления с поддержкой множества устройств",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }

    // Диалог выбора базы данных
    if (showImportDialog) {
        AlertDialog(
            onDismissRequest = { showImportDialog = false },
            title = { Text("Выберите базу данных") },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Выберите источник для импорта IR кодов:")

                    OutlinedButton(
                        onClick = {
                            showImportDialog = false
                            viewModel.importIRDB()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text("IRDB", style = MaterialTheme.typography.titleSmall)
                            Text(
                                "Основная база IR кодов",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    OutlinedButton(
                        onClick = {
                            showImportDialog = false
                            viewModel.importFlipperIRDB()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text("Flipper-IRDB", style = MaterialTheme.typography.titleSmall)
                            Text(
                                "База от Flipper Zero",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showImportDialog = false }) {
                    Text("Отмена")
                }
            }
        )
    }

    // Диалог успешного импорта
    if (state.showImportSuccess) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissImportDialog() },
            icon = { Icon(Icons.Default.CheckCircle, contentDescription = null) },
            title = { Text("Импорт завершён") },
            text = { Text(state.importMessage) },
            confirmButton = {
                TextButton(onClick = { viewModel.dismissImportDialog() }) {
                    Text("OK")
                }
            }
        )
    }

    // Диалог ошибки импорта
    if (state.showImportError) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissImportDialog() },
            icon = { Icon(Icons.Default.Error, contentDescription = null) },
            title = { Text("Ошибка импорта") },
            text = { Text(state.importMessage) },
            confirmButton = {
                TextButton(onClick = { viewModel.dismissImportDialog() }) {
                    Text("OK")
                }
            }
        )
    }
}
