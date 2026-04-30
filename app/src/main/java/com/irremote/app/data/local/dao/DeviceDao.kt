package com.irremote.app.data.local.dao

import androidx.room.*
import com.irremote.app.data.local.entity.DeviceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {
    @Query("SELECT * FROM devices ORDER BY isFavorite DESC, updatedAt DESC")
    fun getAllDevices(): Flow<List<DeviceEntity>>

    @Query("SELECT * FROM devices WHERE id = :deviceId")
    suspend fun getDeviceById(deviceId: Long): DeviceEntity?

    @Query("SELECT * FROM devices WHERE type = :type ORDER BY updatedAt DESC")
    fun getDevicesByType(type: String): Flow<List<DeviceEntity>>

    @Query("SELECT * FROM devices WHERE isFavorite = 1 ORDER BY updatedAt DESC")
    fun getFavoriteDevices(): Flow<List<DeviceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDevice(device: DeviceEntity): Long

    @Update
    suspend fun updateDevice(device: DeviceEntity)

    @Delete
    suspend fun deleteDevice(device: DeviceEntity)

    @Query("UPDATE devices SET isFavorite = :isFavorite WHERE id = :deviceId")
    suspend fun updateFavoriteStatus(deviceId: Long, isFavorite: Boolean)

    @Query("DELETE FROM devices")
    suspend fun deleteAllDevices()
}
