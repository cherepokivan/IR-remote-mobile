package ru.cherepokivan.irremote.di

import ru.cherepokivan.irremote.domain.repository.DeviceRepository
import ru.cherepokivan.irremote.domain.repository.IRCommandRepository
import ru.cherepokivan.irremote.domain.repository.IRDBRepository
import ru.cherepokivan.irremote.domain.service.IRTransmitterService
import ru.cherepokivan.irremote.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideDeviceUseCases(repository: DeviceRepository): DeviceUseCases {
        return DeviceUseCases(
            getAllDevices = GetAllDevicesUseCase(repository),
            getDeviceById = GetDeviceByIdUseCase(repository),
            addDevice = AddDeviceUseCase(repository),
            updateDevice = UpdateDeviceUseCase(repository),
            deleteDevice = DeleteDeviceUseCase(repository),
            getFavoriteDevices = GetFavoriteDevicesUseCase(repository),
            toggleFavorite = ToggleFavoriteUseCase(repository),
            searchDevices = SearchDevicesUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideIRCommandUseCases(repository: IRCommandRepository): IRCommandUseCases {
        return IRCommandUseCases(
            getCommandsByDeviceId = GetCommandsByDeviceIdUseCase(repository),
            addCommand = AddCommandUseCase(repository),
            updateCommand = UpdateCommandUseCase(repository),
            deleteCommand = DeleteCommandUseCase(repository),
            getCommandById = GetCommandByIdUseCase(repository),
            deleteCommandsByDeviceId = DeleteCommandsByDeviceIdUseCase(repository),
            searchCommands = SearchCommandsUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideIRTransmitterUseCases(service: IRTransmitterService): IRTransmitterUseCases {
        return IRTransmitterUseCases(
            transmitIRCommand = TransmitIRCommandUseCase(service),
            checkIRAvailability = CheckIRAvailabilityUseCase(service),
            getCarrierFrequencies = GetCarrierFrequenciesUseCase(service)
        )
    }

    @Provides
    @Singleton
    fun provideIRDBUseCases(repository: IRDBRepository): IRDBUseCases {
        return IRDBUseCases(
            importIRDB = ImportIRDBUseCase(repository),
            importFlipperIRDB = ImportFlipperIRDBUseCase(repository),
            searchOnlineDevices = SearchOnlineDevicesUseCase(repository)
        )
    }
}
