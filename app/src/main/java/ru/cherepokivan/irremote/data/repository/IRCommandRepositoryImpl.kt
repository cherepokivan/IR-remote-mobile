package ru.cherepokivan.irremote.data.repository

import ru.cherepokivan.irremote.data.local.dao.IRCommandDao
import ru.cherepokivan.irremote.data.mapper.IRCommandMapper
import ru.cherepokivan.irremote.domain.model.IRCommand
import ru.cherepokivan.irremote.domain.repository.IRCommandRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IRCommandRepositoryImpl @Inject constructor(
    private val commandDao: IRCommandDao
) : IRCommandRepository {

    override fun getCommandsByDeviceId(deviceId: Long): Flow<List<IRCommand>> {
        return commandDao.getCommandsByDeviceId(deviceId).map { entities ->
            IRCommandMapper.toDomainList(entities)
        }
    }

    override suspend fun getCommandById(id: Long): IRCommand? {
        return commandDao.getCommandById(id)?.let { IRCommandMapper.toDomain(it) }
    }

    override fun searchCommands(deviceId: Long, query: String): Flow<List<IRCommand>> {
        return commandDao.searchCommands(deviceId, query).map { entities ->
            IRCommandMapper.toDomainList(entities)
        }
    }

    override suspend fun addCommand(command: IRCommand): Long {
        return commandDao.insert(IRCommandMapper.toEntity(command))
    }

    override suspend fun updateCommand(command: IRCommand) {
        commandDao.update(IRCommandMapper.toEntity(command))
    }

    override suspend fun deleteCommand(command: IRCommand) {
        commandDao.delete(IRCommandMapper.toEntity(command))
    }

    override suspend fun deleteCommandsByDeviceId(deviceId: Long) {
        commandDao.deleteByDeviceId(deviceId)
    }
}
