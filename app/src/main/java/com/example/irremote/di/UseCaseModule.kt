package com.example.irremote.di

import com.example.irremote.domain.repository.DeviceRepository
import com.example.irremote.domain.repository.IRCommandRepository
import com.example.irremote.domain.repository.IRDBRepository
import com.example.irremote.domain.service.IRTransmitterService
import com.example.irremote.domain.usecase.*
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
