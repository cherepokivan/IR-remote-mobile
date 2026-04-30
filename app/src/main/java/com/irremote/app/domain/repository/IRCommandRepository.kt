package com.irremote.app.domain.repository

import com.irremote.app.domain.model.IRCommand
import kotlinx.coroutines.flow.Flow

interface IRCommandRepository {
    fun getCommandsByDeviceId(deviceId: Long): Flow<List<IRCommand>>

    suspend fun getCommandById(commandId: Long): IRCommand?

    suspend fun getCommandByName(deviceId: Long, commandName: String): IRCommand?

    suspend fun insertCommand(command: IRCommand): Long

    suspend fun insertCommands(commands: List<IRCommand>)

    suspend fun updateCommand(command: IRCommand)

    suspend fun deleteCommand(command: IRCommand)

    suspend fun deleteCommandsByDeviceId(deviceId: Long)

    suspend fun deleteAllCommands()
}
