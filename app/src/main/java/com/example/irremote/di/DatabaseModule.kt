package com.example.irremote.di

import android.content.Context
import androidx.room.Room
import com.example.irremote.data.local.IRRemoteDatabase
import com.example.irremote.data.local.dao.DeviceDao
import com.example.irremote.data.local.dao.IRCommandDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): IRRemoteDatabase {
        return Room.databaseBuilder(
            context,
            IRRemoteDatabase::class.java,
            "ir_remote_database"
        ).build()
    }

    @Provides
    fun provideDeviceDao(database: IRRemoteDatabase): DeviceDao {
        return database.deviceDao()
    }

    @Provides
    fun provideIRCommandDao(database: IRRemoteDatabase): IRCommandDao {
        return database.irCommandDao()
    }
}
