package com.firghi0101.assesment1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.firghi0101.assesment1.ui.screen.FormScreen
import com.firghi0101.assesment1.ui.screen.MainScreen

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {

        composable(Screen.Main.route) {
            MainScreen(navController)
        }

        composable(Screen.Form.route) {
            FormScreen(navController)
        }
    }
}