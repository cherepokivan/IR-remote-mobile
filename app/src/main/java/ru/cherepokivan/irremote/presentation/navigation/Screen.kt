package ru.cherepokivan.irremote.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object RemoteControl : Screen("remote/{deviceId}") {
        fun createRoute(deviceId: Long) = "remote/$deviceId"
    }
    object AddDevice : Screen("add_device")
    object DeviceList : Screen("device_list")
    object Settings : Screen("settings")
}
