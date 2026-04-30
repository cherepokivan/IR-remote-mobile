package com.irremote.app.di

import com.irremote.app.data.service.IRTransmitterServiceImpl
import com.irremote.app.domain.service.IRTransmitterService
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
