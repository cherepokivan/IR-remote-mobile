package ru.cherepokivan.irremote.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO для устройства из IRDB
 */
data class IRDBDeviceDto(
    @SerializedName("brand")
    val brand: String,

    @SerializedName("model")
    val model: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("protocol")
    val protocol: String,

    @SerializedName("commands")
    val commands: Map<String, IRDBCommandDto>
)

/**
 * DTO для IR команды из IRDB
 */
data class IRDBCommandDto(
    @SerializedName("protocol")
    val protocol: String? = null,

    @SerializedName("address")
    val address: String? = null,

    @SerializedName("command")
    val command: String? = null,

    @SerializedName("frequency")
    val frequency: Int? = null,

    @SerializedName("data")
    val data: String? = null,

    @SerializedName("raw")
    val raw: List<Int>? = null
)
