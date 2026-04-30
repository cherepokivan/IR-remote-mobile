package com.example.irremote.domain.repository

import com.example.irremote.data.repository.ImportProgress
import com.example.irremote.domain.model.Device
import kotlinx.coroutines.flow.Flow

/**
 * Репозиторий для работы с онлайн базами IR кодов
 */
interface IRDBRepository {

    /**
     * Загрузить и импортировать базу IRDB
     */
    suspend fun downloadAndImportIRDB(): Flow<ImportProgress>

    /**
     * Загрузить и импортировать базу Flipper-IRDB
     */
    suspend fun downloadAndImportFlipperIRDB(): Flow<ImportProgress>

    /**
     * Поиск устройств в онлайн базе
     */
    suspend fun searchOnlineDevices(query: String): List<Device>
}
