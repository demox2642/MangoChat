package com.example.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.presentation.screens.chat.ChatScreen

sealed class ChatScreens(
    val route: String,
) {
    object ChatScreen : ChatScreens("chat_screen")
}

fun NavGraphBuilder.chatScreens(navController: NavHostController)  {
    composable(ChatScreens.ChatScreen.route) { ChatScreen() }
}
