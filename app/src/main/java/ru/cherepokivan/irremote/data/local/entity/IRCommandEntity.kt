package ru.cherepokivan.irremote.data.local.entity

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
    val name: String,
    val protocol: String,
    val frequency: Int,
    val pattern: String // JSON array of integers
)
