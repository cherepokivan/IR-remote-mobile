package com.irremote.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.irremote.app.presentation.screen.adddevice.AddDeviceScreen
import com.irremote.app.presentation.screen.devicelist.DeviceListScreen
import com.irremote.app.presentation.screen.home.HomeScreen
import com.irremote.app.presentation.screen.remote.RemoteControlScreen
import com.irremote.app.presentation.screen.settings.SettingsScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToDeviceList = {
                    navController.navigate(Screen.DeviceList.route)
                },
                onNavigateToDevice = { deviceId ->
                    navController.navigate(Screen.RemoteControl.createRoute(deviceId))
                },
                onNavigateToAddDevice = {
                    navController.navigate(Screen.AddDevice.route)
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }

        composable(Screen.DeviceList.route) {
            DeviceListScreen(
                onNavigateBack = { navController.navigateUp() },
                onNavigateToDevice = { deviceId ->
                    navController.navigate(Screen.RemoteControl.createRoute(deviceId))
                }
            )
        }

        composable(
            route = Screen.DeviceDetail.route,
            arguments = listOf(
                navArgument("deviceId") { type = NavType.LongType }
            )
        ) {
            // DeviceDetailScreen will be implemented later
        }

        composable(Screen.AddDevice.route) {
            AddDeviceScreen(
                onNavigateBack = { navController.navigateUp() },
                onDeviceAdded = { deviceId ->
                    navController.navigate(Screen.RemoteControl.createRoute(deviceId)) {
                        popUpTo(Screen.Home.route)
                    }
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
                onNavigateBack = { navController.navigateUp() }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}
