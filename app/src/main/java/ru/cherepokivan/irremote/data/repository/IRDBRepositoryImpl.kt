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
            emit(ImportProgress.Loading(0, "Загрузка устройств..."))

            // Загружаем 3 популярных устройства для теста
            val devices = mutableListOf<Pair<String, String>>()

            try {
                emit(ImportProgress.Loading(10, "Загрузка Samsung TV..."))
                val samsungJson = irdbApi.getSamsungTV()
                devices.add("Samsung" to samsungJson)
            } catch (e: Exception) {
                emit(ImportProgress.Loading(10, "Samsung TV недоступен"))
            }

            try {
                emit(ImportProgress.Loading(40, "Загрузка LG TV..."))
                val lgJson = irdbApi.getLGTV()
                devices.add("LG" to lgJson)
            } catch (e: Exception) {
                emit(ImportProgress.Loading(40, "LG TV недоступен"))
            }

            try {
                emit(ImportProgress.Loading(70, "Загрузка Sony TV..."))
                val sonyJson = irdbApi.getSonyTV()
                devices.add("Sony" to sonyJson)
            } catch (e: Exception) {
                emit(ImportProgress.Loading(70, "Sony TV недоступен"))
            }

            if (devices.isEmpty()) {
                emit(ImportProgress.Error("Не удалось загрузить ни одного устройства"))
                return@flow
            }

            emit(ImportProgress.Loading(80, "Импорт в базу данных..."))

            // Импортируем загруженные устройства
            devices.forEachIndexed { index, (brand, json) ->
                try {
                    val deviceDto = IRDBMapper.parseJson(brand, json)
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
                } catch (e: Exception) {
                    // Пропускаем проблемные устройства
                }
            }

            emit(ImportProgress.Success(devices.size))
        } catch (e: Exception) {
            emit(ImportProgress.Error(e.message ?: "Неизвестная ошибка"))
        }
    }

    override suspend fun downloadAndImportFlipperIRDB(): Flow<ImportProgress> = flow {
        try {
            emit(ImportProgress.Loading(0, "Flipper-IRDB пока не поддерживается"))
            emit(ImportProgress.Error("Функция в разработке"))
        } catch (e: Exception) {
            emit(ImportProgress.Error(e.message ?: "Неизвестная ошибка"))
        }
    }

    override suspend fun searchOnlineDevices(query: String): List<Device> {
        // Поиск пока не реализован, возвращаем пустой список
        return emptyList()
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
