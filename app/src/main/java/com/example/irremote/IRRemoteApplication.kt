package com.example.irremote

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.irremote.data.local.SampleDataProvider
import com.example.irremote.domain.repository.DeviceRepository
import com.example.irremote.domain.repository.IRCommandRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class IRRemoteApplication : Application() {

    @Inject
    lateinit var deviceRepository: DeviceRepository

    @Inject
    lateinit var commandRepository: IRCommandRepository

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate() {
        super.onCreate()
        loadSampleDataIfNeeded()
    }

    private fun loadSampleDataIfNeeded() {
        CoroutineScope(Dispatchers.IO).launch {
            val isFirstLaunch = dataStore.data.first()[FIRST_LAUNCH_KEY] ?: true

            if (isFirstLaunch) {
                // Загружаем тестовые данные
                val sampleData = SampleDataProvider.getSampleDevices()

                sampleData.forEach { (device, commands) ->
                    val deviceId = deviceRepository.addDevice(device)
                    commands.forEach { command ->
                        commandRepository.addCommand(command.copy(deviceId = deviceId))
                    }
                }

                // Отмечаем, что первый запуск завершён
                dataStore.edit { preferences ->
                    preferences[FIRST_LAUNCH_KEY] = false
                }
            }
        }
    }

    companion object {
        private val FIRST_LAUNCH_KEY = booleanPreferencesKey("first_launch")
    }
}
