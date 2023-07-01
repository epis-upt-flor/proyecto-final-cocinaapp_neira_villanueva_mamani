package com.example.cocinafeliz.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cocinafeliz.screens.CocinaSplashScreen
import com.example.cocinafeliz.screens.home.CocinaHomeScreen
import com.example.cocinafeliz.screens.login.CocinaLoginScreen
import com.example.cocinafeliz.maps.MapasScreen
import com.example.cocinafeliz.notificacion.Notificaciones
import com.example.cocinafeliz.screens.chatgpt.ChatScreen
import com.example.cocinafeliz.screens.home.DatosUser
import com.example.cocinafeliz.screens.home.Soporte
import com.example.cocinafeliz.screens.login.LoginScreenViewModel

@Composable
fun CocinaNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = CocinaScreens.SplashScreem.name
    ) {
        composable(CocinaScreens.SplashScreem.name) {
            CocinaSplashScreen(navController = navController)
        }
        composable(CocinaScreens.Notificacion.name) {
            Notificaciones()
        }
        composable(CocinaScreens.LoginScreen.name) {
            CocinaLoginScreen(navController = navController)
        }
        composable(CocinaScreens.CocinaHomeScreen.name) {
            CocinaHomeScreen(navController = navController)
        }
        composable(CocinaScreens.MapasScreen.name) {
            MapasScreen(navController = navController)
        }
        composable(CocinaScreens.Soporte.name) {
            Soporte(navController = navController)
        }
        // Nueva ruta para DatosUser
        composable(
            route = "${CocinaScreens.DatosUser.name}/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            val viewModel: LoginScreenViewModel = viewModel() // Instancia del LoginScreenViewModel
            if (userId != null) {
                DatosUser(userId = userId, viewModel = viewModel, navController = navController)
            }
        }
    }
}