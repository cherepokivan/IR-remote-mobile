package com.irremote.app.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.irremote.app.domain.model.Device
import com.irremote.app.domain.model.DeviceType

@Composable
fun DeviceCard(
    device: Device,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
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
                        text = "${device.brand}${device.model?.let { " - $it" } ?: ""}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            IconButton(onClick = onToggleFavorite) {
                Icon(
                    imageVector = if (device.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (device.isFavorite) "Remove from favorites" else "Add to favorites",
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
    DeviceType.SET_TOP_BOX -> Icons.Default.SettopBox
    DeviceType.DVD_PLAYER -> Icons.Default.Album
    DeviceType.OTHER -> Icons.Default.Devices
}
