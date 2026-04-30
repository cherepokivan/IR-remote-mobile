package com.irremote.app.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object DeviceList : Screen("device_list")
    object DeviceDetail : Screen("device_detail/{deviceId}") {
        fun createRoute(deviceId: Long) = "device_detail/$deviceId"
    }
    object AddDevice : Screen("add_device")
    object RemoteControl : Screen("remote_control/{deviceId}") {
        fun createRoute(deviceId: Long) = "remote_control/$deviceId"
    }
    object Settings : Screen("settings")
}
