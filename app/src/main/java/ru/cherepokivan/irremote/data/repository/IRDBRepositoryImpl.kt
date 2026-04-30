package ru.cherepokivan.irremote.data.repository

import ru.cherepokivan.irremote.data.local.dao.DeviceDao
import ru.cherepokivan.irremote.data.local.dao.IRCommandDao
import ru.cherepokivan.irremote.data.mapper.DeviceMapper
import ru.cherepokivan.irremote.data.mapper.IRCommandMapper
import ru.cherepokivan.irremote.data.mapper.IRDBMapper
import ru.cherepokivan.irremote.data.remote.IRDBApi
import ru.cherepokivan.irremote.domain.model.Device
import ru.cherepokivan.irremote.domain.repository.IRDBRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Реализация репозитория для работы с IRDB
 */
@Singleton
class IRDBRepositoryImpl @Inject constructor(
    private val irdbApi: IRDBApi,
    private val deviceDao: DeviceDao,
    private val commandDao: IRCommandDao
) : IRDBRepository {

    override suspend fun downloadAndImportIRDB(): Flow<ImportProgress> = flow {
        try {
            emit(ImportProgress.Loading(0, "Загрузка списка устройств..."))

            // Загружаем индекс IRDB
            val devices = try {
                irdbApi.getIRDBIndex()
            } catch (e: Exception) {
                emit(ImportProgress.Error("Ошибка загрузки: ${e.message}"))
                return@flow
            }

            val total = devices.size

            if (total == 0) {
                emit(ImportProgress.Error("База данных пуста"))
                return@flow
            }

            emit(ImportProgress.Loading(0, "Найдено устройств: $total"))

            // Импортируем каждое устройство
            devices.forEachIndexed { index, deviceDto ->
                try {
                    // Преобразуем в domain модель
                    val device = IRDBMapper.toDomain(deviceDto)

                    // Сохраняем устройство
                    val deviceEntity = DeviceMapper.toEntity(device)
                    val deviceId = deviceDao.insert(deviceEntity)

                    // Сохраняем команды
                    val commands = IRDBMapper.commandsToDomain(
                        deviceId = deviceId,
                        commands = deviceDto.commands,
                        defaultProtocol = deviceDto.protocol
                    )

                    val commandEntities = commands.map { IRCommandMapper.toEntity(it) }
                    commandDao.insertAll(commandEntities)

                    // Обновляем прогресс
                    val progress = ((index + 1) * 100) / total
                    emit(ImportProgress.Loading(
                        progress = progress,
                        message = "Импортировано: ${index + 1}/$total"
                    ))

                } catch (e: Exception) {
                    // Пропускаем проблемные устройства
                    emit(ImportProgress.Loading(
                        progress = ((index + 1) * 100) / total,
                        message = "Ошибка при импорте ${deviceDto.brand} ${deviceDto.model}"
                    ))
                }
            }

            emit(ImportProgress.Success(total))

        } catch (e: Exception) {
            emit(ImportProgress.Error(e.message ?: "Неизвестная ошибка"))
        }
    }

    override suspend fun downloadAndImportFlipperIRDB(): Flow<ImportProgress> = flow {
        try {
            emit(ImportProgress.Loading(0, "Загрузка Flipper-IRDB..."))

            val devices = irdbApi.getFlipperIndex()
            val total = devices.size

            emit(ImportProgress.Loading(0, "Найдено устройств: $total"))

            devices.forEachIndexed { index, deviceDto ->
                try {
                    val device = IRDBMapper.toDomain(deviceDto)
                    val deviceEntity = DeviceMapper.toEntity(device)
                    val deviceId = deviceDao.insert(deviceEntity)

                    val commands = IRDBMapper.commandsToDomain(
                        deviceId = deviceId,
                        commands = deviceDto.commands,
                        defaultProtocol = deviceDto.protocol
                    )

                    val commandEntities = commands.map { IRCommandMapper.toEntity(it) }
                    commandDao.insertAll(commandEntities)

                    val progress = ((index + 1) * 100) / total
                    emit(ImportProgress.Loading(
                        progress = progress,
                        message = "Импортировано: ${index + 1}/$total"
                    ))

                } catch (e: Exception) {
                    emit(ImportProgress.Loading(
                        progress = ((index + 1) * 100) / total,
                        message = "Ошибка при импорте ${deviceDto.brand} ${deviceDto.model}"
                    ))
                }
            }

            emit(ImportProgress.Success(total))

        } catch (e: Exception) {
            emit(ImportProgress.Error(e.message ?: "Неизвестная ошибка"))
        }
    }

    override suspend fun searchOnlineDevices(query: String): List<Device> {
        return try {
            val devices = irdbApi.getIRDBIndex()
            devices
                .filter {
                    it.brand.contains(query, ignoreCase = true) ||
                    it.model.contains(query, ignoreCase = true)
                }
                .map { IRDBMapper.toDomain(it) }
        } catch (e: Exception) {
            emptyList()
        }
    }
}

/**
 * Прогресс импорта базы данных
 */
sealed class ImportProgress {
    data class Loading(val progress: Int, val message: String) : ImportProgress()
    data class Success(val importedCount: Int) : ImportProgress()
    data class Error(val message: String) : ImportProgress()
}
