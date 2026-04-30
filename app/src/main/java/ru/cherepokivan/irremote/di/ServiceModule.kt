package ru.cherepokivan.irremote.di

import ru.cherepokivan.irremote.data.service.IRTransmitterServiceImpl
import ru.cherepokivan.irremote.domain.service.IRTransmitterService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    @Singleton
    abstract fun bindIRTransmitterService(
        irTransmitterServiceImpl: IRTransmitterServiceImpl
    ): IRTransmitterService
}
