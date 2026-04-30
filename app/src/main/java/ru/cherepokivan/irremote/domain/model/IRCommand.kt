package ru.cherepokivan.irremote.domain.model

data class IRCommand(
    val id: Long = 0,
    val deviceId: Long,
    val name: String,
    val protocol: IRProtocol,
    val frequency: Int = 38000,
    val pattern: List<Int>
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
    RAW
}
