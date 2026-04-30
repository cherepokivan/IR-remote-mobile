package com.example.irremote.domain.repository

import com.example.irremote.domain.model.IRCommand
import kotlinx.coroutines.flow.Flow

interface IRCommandRepository {
    fun getCommandsByDeviceId(deviceId: Long): Flow<List<IRCommand>>
    suspend fun getCommandById(id: Long): IRCommand?
    fun searchCommands(deviceId: Long, query: String): Flow<List<IRCommand>>
    suspend fun addCommand(command: IRCommand): Long
    suspend fun updateCommand(command: IRCommand)
    suspend fun deleteCommand(command: IRCommand)
    suspend fun deleteCommandsByDeviceId(deviceId: Long)
}
