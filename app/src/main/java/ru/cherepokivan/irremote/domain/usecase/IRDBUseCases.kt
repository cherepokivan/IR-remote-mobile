package ru.cherepokivan.irremote.domain.usecase

import ru.cherepokivan.irremote.data.repository.ImportProgress
import ru.cherepokivan.irremote.domain.model.Device
import ru.cherepokivan.irremote.domain.repository.IRDBRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use cases для работы с IRDB
 */
data class IRDBUseCases(
    val importIRDB: ImportIRDBUseCase,
    val importFlipperIRDB: ImportFlipperIRDBUseCase,
    val searchOnlineDevices: SearchOnlineDevicesUseCase
)

class ImportIRDBUseCase @Inject constructor(
    private val repository: IRDBRepository
) {
    suspend operator fun invoke(): Flow<ImportProgress> {
        return repository.downloadAndImportIRDB()
    }
}

class ImportFlipperIRDBUseCase @Inject constructor(
    private val repository: IRDBRepository
) {
    suspend operator fun invoke(): Flow<ImportProgress> {
        return repository.downloadAndImportFlipperIRDB()
    }
}

class SearchOnlineDevicesUseCase @Inject constructor(
    private val repository: IRDBRepository
) {
    suspend operator fun invoke(query: String): List<Device> {
        return repository.searchOnlineDevices(query)
    }
}
