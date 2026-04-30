package com.example.irremote.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.irremote.presentation.screen.adddevice.AddDeviceScreen
import com.example.irremote.presentation.screen.devicelist.DeviceListScreen
import com.example.irremote.presentation.screen.home.HomeScreen
import com.example.irremote.presentation.screen.remote.RemoteControlScreen
import com.example.irremote.presentation.screen.settings.SettingsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToRemote = { deviceId ->
                    navController.navigate(Screen.RemoteControl.createRoute(deviceId))
                },
                onNavigateToAddDevice = {
                    navController.navigate(Screen.AddDevice.route)
                },
                onNavigateToDeviceList = {
                    navController.navigate(Screen.DeviceList.route)
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }

        composable(
            route = Screen.RemoteControl.route,
            arguments = listOf(
                navArgument("deviceId") { type = NavType.LongType }
            )
        ) {
            RemoteControlScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.AddDevice.route) {
            AddDeviceScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.DeviceList.route) {
            DeviceListScreen(
                onNavigateToRemote = { deviceId ->
                    navController.navigate(Screen.RemoteControl.createRoute(deviceId))
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
