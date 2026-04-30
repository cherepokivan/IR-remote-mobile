package ru.cherepokivan.irremote.di

import ru.cherepokivan.irremote.data.repository.DeviceRepositoryImpl
import ru.cherepokivan.irremote.data.repository.IRCommandRepositoryImpl
import ru.cherepokivan.irremote.data.repository.IRDBRepositoryImpl
import ru.cherepokivan.irremote.domain.repository.DeviceRepository
import ru.cherepokivan.irremote.domain.repository.IRCommandRepository
import ru.cherepokivan.irremote.domain.repository.IRDBRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDeviceRepository(
        deviceRepositoryImpl: DeviceRepositoryImpl
    ): DeviceRepository

    @Binds
    @Singleton
    abstract fun bindIRCommandRepository(
        irCommandRepositoryImpl: IRCommandRepositoryImpl
    ): IRCommandRepository

    @Binds
    @Singleton
    abstract fun bindIRDBRepository(
        irdbRepositoryImpl: IRDBRepositoryImpl
    ): IRDBRepository
}
