package ru.cherepokivan.irremote.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.cherepokivan.irremote.data.local.dao.DeviceDao
import ru.cherepokivan.irremote.data.local.dao.IRCommandDao
import ru.cherepokivan.irremote.data.local.entity.DeviceEntity
import ru.cherepokivan.irremote.data.local.entity.IRCommandEntity

@Database(
    entities = [DeviceEntity::class, IRCommandEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class IRRemoteDatabase : RoomDatabase() {
    abstract fun deviceDao(): DeviceDao
    abstract fun irCommandDao(): IRCommandDao
}
