package com.example.presentation

import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.presentation.screens.chat.ChatScreen
import com.example.presentation.screens.chat_detail.ChatDetailScreen

sealed class ChatScreens(
    val route: String,
) {
    object ChatScreen : ChatScreens("chat_screen")

    object ChatDeteilScreen : ChatScreens("chat_detail")
}

fun NavGraphBuilder.chatScreens(navController: NavHostController) {
    composable(ChatScreens.ChatScreen.route) { ChatScreen(navController) }
    composable(ChatScreens.ChatDeteilScreen.route.plus("/{chat_id}"),
        arguments =
        listOf(
            navArgument(name = "chat_id") {
                type = NavType.LongType
            },
        ),
        ) { backStackEntry ->
        val args =
            remember(backStackEntry) {
                backStackEntry.arguments?.getLong("chat_id")
            }
        ChatDetailScreen(args) }
}
