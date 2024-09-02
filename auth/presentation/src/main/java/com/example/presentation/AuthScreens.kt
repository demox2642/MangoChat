package com.example.presentation

import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.presentation.screens.code.CodeScreen
import com.example.presentation.screens.phone.PhoneScreen
import com.example.presentation.screens.registration.RegistrationScreen

sealed class AuthScreens(
    val route: String,
) {
    object CodeScreen : AuthScreens("code_screen")

    object PhoneScreen : AuthScreens("phone_screen")

    object RegistrationScreen : AuthScreens("registration_screen")
}

fun NavGraphBuilder.authScreens(navController: NavHostController) {
    composable(
        AuthScreens.CodeScreen.route.plus("/{phone_num}"),
        arguments =
            listOf(
                navArgument(name = "phone_num") {
                    type = NavType.StringType
                },
            ),
    ) { backStackEntry ->
        val args =
            remember(backStackEntry) {
                backStackEntry.arguments?.getString("phone_num")
            }
        CodeScreen(navController, args)
    }
    composable(AuthScreens.PhoneScreen.route) { PhoneScreen(navController) }
    composable(
        AuthScreens.RegistrationScreen.route.plus("/{phone_num}"),
        arguments =
            listOf(
                navArgument(name = "phone_num") {
                    type = NavType.StringType
                },
            ),
    ) { backStackEntry ->
        val args =
            remember(backStackEntry) {
                backStackEntry.arguments?.getString("phone_num")
            }
        RegistrationScreen(navController, args)
    }
}
