package com.example.irremote.di

import com.example.irremote.data.service.IRTransmitterServiceImpl
import com.example.irremote.domain.service.IRTransmitterService
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
