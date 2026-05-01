package ru.cherepokivan.irremote.data.remote

import retrofit2.http.GET

/**
 * API для загрузки IR баз данных из GitHub
 * Используем прямые ссылки на конкретные файлы
 */
interface IRDBApi {

    /**
     * Получить тестовые устройства из IRDB
     * Загружаем несколько популярных устройств для начала
     */
    @GET("probonopd/irdb/refs/heads/master/codes/Samsung/TV.json")
    suspend fun getSamsungTV(): String

    @GET("probonopd/irdb/refs/heads/master/codes/LG/TV.json")
    suspend fun getLGTV(): String

    @GET("probonopd/irdb/refs/heads/master/codes/Sony/TV.json")
    suspend fun getSonyTV(): String
}
