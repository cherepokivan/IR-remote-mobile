package com.irremote.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.irremote.app.data.local.dao.DeviceDao
import com.irremote.app.data.local.dao.IRCommandDao
import com.irremote.app.data.local.entity.DeviceEntity
import com.irremote.app.data.local.entity.IRCommandEntity

@Database(
    entities = [
        DeviceEntity::class,
        IRCommandEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class IRRemoteDatabase : RoomDatabase() {
    abstract fun deviceDao(): DeviceDao
    abstract fun irCommandDao(): IRCommandDao
}
