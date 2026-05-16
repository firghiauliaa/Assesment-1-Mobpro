package com.firghi0101.assesment1.navigation

sealed class Screen(val route: String) {

    data object Main : Screen("main")

    data object Form : Screen("calculator")

    data object Edit : Screen("calculator_screen/{id}") {
        fun withId(id: Long) = "calculator_screen/$id"
    }
}