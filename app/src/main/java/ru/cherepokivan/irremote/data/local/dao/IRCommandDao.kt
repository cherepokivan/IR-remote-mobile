package ru.cherepokivan.irremote.data.local.dao

import androidx.room.*
import ru.cherepokivan.irremote.data.local.entity.IRCommandEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IRCommandDao {

    @Query("SELECT * FROM ir_commands WHERE deviceId = :deviceId ORDER BY name ASC")
    fun getCommandsByDeviceId(deviceId: Long): Flow<List<IRCommandEntity>>

    @Query("SELECT * FROM ir_commands WHERE id = :id")
    suspend fun getCommandById(id: Long): IRCommandEntity?

    @Query("SELECT * FROM ir_commands WHERE deviceId = :deviceId AND name LIKE '%' || :query || '%'")
    fun searchCommands(deviceId: Long, query: String): Flow<List<IRCommandEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(command: IRCommandEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(commands: List<IRCommandEntity>)

    @Update
    suspend fun update(command: IRCommandEntity)

    @Delete
    suspend fun delete(command: IRCommandEntity)

    @Query("DELETE FROM ir_commands WHERE deviceId = :deviceId")
    suspend fun deleteByDeviceId(deviceId: Long)

    @Query("DELETE FROM ir_commands")
    suspend fun deleteAll()
}
