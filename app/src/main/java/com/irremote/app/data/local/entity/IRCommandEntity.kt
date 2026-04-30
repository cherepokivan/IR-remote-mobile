package com.irremote.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ir_commands",
    foreignKeys = [
        ForeignKey(
            entity = DeviceEntity::class,
            parentColumns = ["id"],
            childColumns = ["deviceId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("deviceId")]
)
data class IRCommandEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val deviceId: Long,
    val name: String, // POWER, VOL_UP, VOL_DOWN, CH_UP, CH_DOWN, etc.
    val protocol: String, // NEC, RC5, RC6, SAMSUNG, LG, etc.
    val frequency: Int, // Carrier frequency in Hz (usually 38000)
    val pattern: String, // JSON array of pulse/space timings in microseconds
    val createdAt: Long = System.currentTimeMillis()
)
