package com.irremote.app.data.repository

import com.irremote.app.data.local.dao.IRCommandDao
import com.irremote.app.data.mapper.toDomain
import com.irremote.app.data.mapper.toEntity
import com.irremote.app.domain.model.IRCommand
import com.irremote.app.domain.repository.IRCommandRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IRCommandRepositoryImpl @Inject constructor(
    private val irCommandDao: IRCommandDao
) : IRCommandRepository {

    override fun getCommandsByDeviceId(deviceId: Long): Flow<List<IRCommand>> {
        return irCommandDao.getCommandsByDeviceId(deviceId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getCommandById(commandId: Long): IRCommand? {
        return irCommandDao.getCommandById(commandId)?.toDomain()
    }

    override suspend fun getCommandByName(deviceId: Long, commandName: String): IRCommand? {
        return irCommandDao.getCommandByName(deviceId, commandName)?.toDomain()
    }

    override suspend fun insertCommand(command: IRCommand): Long {
        return irCommandDao.insertCommand(command.toEntity())
    }

    override suspend fun insertCommands(commands: List<IRCommand>) {
        irCommandDao.insertCommands(commands.map { it.toEntity() })
    }

    override suspend fun updateCommand(command: IRCommand) {
        irCommandDao.updateCommand(command.toEntity())
    }

    override suspend fun deleteCommand(command: IRCommand) {
        irCommandDao.deleteCommand(command.toEntity())
    }

    override suspend fun deleteCommandsByDeviceId(deviceId: Long) {
        irCommandDao.deleteCommandsByDeviceId(deviceId)
    }

    override suspend fun deleteAllCommands() {
        irCommandDao.deleteAllCommands()
    }
}
