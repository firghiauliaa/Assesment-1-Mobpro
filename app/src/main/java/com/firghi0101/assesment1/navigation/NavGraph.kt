package com.firghi0101.assesment1.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.firghi0101.assesment1.ui.screen.CalculatorScreen
import com.firghi0101.assesment1.ui.screen.MainScreen
import com.firghi0101.assesment1.ui.screen.MainViewModel

@Composable
fun NavGraph() {

    val navController = rememberNavController()
    val viewModel: MainViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {

        composable(Screen.Main.route) {
            MainScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(Screen.Form.route) {
            CalculatorScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}