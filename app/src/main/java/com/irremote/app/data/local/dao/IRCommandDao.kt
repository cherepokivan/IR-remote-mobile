package com.irremote.app.data.local.dao

import androidx.room.*
import com.irremote.app.data.local.entity.IRCommandEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IRCommandDao {
    @Query("SELECT * FROM ir_commands WHERE deviceId = :deviceId ORDER BY name ASC")
    fun getCommandsByDeviceId(deviceId: Long): Flow<List<IRCommandEntity>>

    @Query("SELECT * FROM ir_commands WHERE id = :commandId")
    suspend fun getCommandById(commandId: Long): IRCommandEntity?

    @Query("SELECT * FROM ir_commands WHERE deviceId = :deviceId AND name = :commandName")
    suspend fun getCommandByName(deviceId: Long, commandName: String): IRCommandEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommand(command: IRCommandEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommands(commands: List<IRCommandEntity>)

    @Update
    suspend fun updateCommand(command: IRCommandEntity)

    @Delete
    suspend fun deleteCommand(command: IRCommandEntity)

    @Query("DELETE FROM ir_commands WHERE deviceId = :deviceId")
    suspend fun deleteCommandsByDeviceId(deviceId: Long)

    @Query("DELETE FROM ir_commands")
    suspend fun deleteAllCommands()
}
