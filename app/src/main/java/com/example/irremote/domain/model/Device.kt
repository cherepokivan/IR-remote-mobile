package com.example.irremote.domain.model

data class Device(
    val id: Long = 0,
    val name: String,
    val type: DeviceType,
    val brand: String,
    val model: String,
    val isFavorite: Boolean = false
)

enum class DeviceType {
    TV,
    AC,
    FAN,
    PROJECTOR,
    AUDIO,
    SET_TOP_BOX,
    DVD_PLAYER,
    OTHER
}
