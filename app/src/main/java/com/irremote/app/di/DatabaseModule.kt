package com.irremote.app.di

import android.content.Context
import androidx.room.Room
import com.irremote.app.data.local.IRRemoteDatabase
import com.irremote.app.data.local.dao.DeviceDao
import com.irremote.app.data.local.dao.IRCommandDao
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
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDeviceDao(database: IRRemoteDatabase): DeviceDao {
        return database.deviceDao()
    }

    @Provides
    @Singleton
    fun provideIRCommandDao(database: IRRemoteDatabase): IRCommandDao {
        return database.irCommandDao()
    }
}
