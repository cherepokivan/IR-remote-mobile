package com.irremote.app.domain.model

data class IRCommand(
    val id: Long = 0,
    val deviceId: Long,
    val name: String,
    val protocol: IRProtocol,
    val frequency: Int = 38000,
    val pattern: List<Int>,
    val createdAt: Long = System.currentTimeMillis()
)

enum class IRProtocol {
    NEC,
    RC5,
    RC6,
    SAMSUNG,
    LG,
    SONY,
    PANASONIC,
    JVC,
    SHARP,
    DENON,
    DISH,
    WHYNTER,
    COOLIX,
    DAIKIN,
    UNKNOWN;

    companion object {
        fun fromString(value: String): IRProtocol {
            return entries.find { it.name.equals(value, ignoreCase = true) } ?: UNKNOWN
        }
    }
}
