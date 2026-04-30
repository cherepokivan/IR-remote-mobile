package ru.cherepokivan.irremote.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.cherepokivan.irremote.domain.model.Device
import ru.cherepokivan.irremote.domain.model.DeviceType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceCard(
    device: Device,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = getDeviceIcon(device.type),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.primary
                )

                Column {
                    Text(
                        text = device.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "${device.brand} ${device.model}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (device.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Избранное",
                    tint = if (device.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun getDeviceIcon(type: DeviceType) = when (type) {
    DeviceType.TV -> Icons.Default.Tv
    DeviceType.AC -> Icons.Default.AcUnit
    DeviceType.FAN -> Icons.Default.Air
    DeviceType.PROJECTOR -> Icons.Default.Videocam
    DeviceType.AUDIO -> Icons.Default.Speaker
    DeviceType.SET_TOP_BOX -> Icons.Default.Tv
    DeviceType.DVD_PLAYER -> Icons.Default.Album
    DeviceType.OTHER -> Icons.Default.DevicesOther
}
