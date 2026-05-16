package com.firghi0101.assesment1.navigation

sealed class Screen(val route: String) {

    data object Main : Screen("main")

    data object Form : Screen("form")

    data object Edit : Screen("form_screen/{id}") {
        fun withId(id: Long) = "form_screen/$id"
    }
}