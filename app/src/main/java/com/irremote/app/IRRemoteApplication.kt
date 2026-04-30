package com.irremote.app

import android.app.Application
import com.irremote.app.data.local.SampleDataProvider
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class IRRemoteApplication : Application() {

    @Inject
    lateinit var sampleDataProvider: SampleDataProvider

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()

        // Загружаем тестовые данные при первом запуске
        applicationScope.launch {
            sampleDataProvider.insertSampleData()
        }
    }
}
