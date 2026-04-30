package com.example.irremote.di

import com.example.irremote.data.repository.DeviceRepositoryImpl
import com.example.irremote.data.repository.IRCommandRepositoryImpl
import com.example.irremote.data.repository.IRDBRepositoryImpl
import com.example.irremote.domain.repository.DeviceRepository
import com.example.irremote.domain.repository.IRCommandRepository
import com.example.irremote.domain.repository.IRDBRepository
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
