package com.irremote.app.domain.model

data class Device(
    val id: Long = 0,
    val name: String,
    val type: DeviceType,
    val brand: String,
    val model: String?,
    val isFavorite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

enum class DeviceType {
    TV,
    AC,
    FAN,
    PROJECTOR,
    AUDIO,
    SET_TOP_BOX,
    DVD_PLAYER,
    OTHER;

    companion object {
        fun fromString(value: String): DeviceType {
            return entries.find { it.name.equals(value, ignoreCase = true) } ?: OTHER
        }
    }
}
