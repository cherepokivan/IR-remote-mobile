package com.irremote.app.di

import com.irremote.app.data.repository.DeviceRepositoryImpl
import com.irremote.app.data.repository.IRCommandRepositoryImpl
import com.irremote.app.domain.repository.DeviceRepository
import com.irremote.app.domain.repository.IRCommandRepository
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
}
