package com.example.irremote.data.remote

import com.example.irremote.data.remote.dto.IRDBDeviceDto
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * API для загрузки IR баз данных из GitHub
 */
interface IRDBApi {

    /**
     * Получить список всех устройств из IRDB
     */
    @GET("probonopd/irdb/master/codes/index.json")
    suspend fun getIRDBIndex(): List<IRDBDeviceDto>

    /**
     * Получить IR коды для конкретного устройства
     */
    @GET("probonopd/irdb/master/codes/{brand}/{device}.json")
    suspend fun getDeviceCodes(
        @Path("brand") brand: String,
        @Path("device") device: String
    ): IRDBDeviceDto

    /**
     * Получить список устройств из Flipper-IRDB
     */
    @GET("Lucaslhm/Flipper-IRDB/main/index.json")
    suspend fun getFlipperIndex(): List<IRDBDeviceDto>
}
