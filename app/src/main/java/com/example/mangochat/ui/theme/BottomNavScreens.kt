package com.example.mangochat.ui.theme

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.mangochat.R
import com.example.presentation.screens.ProfileScreen
import com.example.presentation.screens.chat.ChatScreen

sealed class BottomNavScreens(
    val route: String,
    @DrawableRes val icon: Int,
    @StringRes val nameId: Int,
) {
    object Chat:BottomNavScreens("chat_screen", R.drawable.ic_chat, R.string.chat)
    object Profile:BottomNavScreens("profile_screen", R.drawable.ic_user, R.string.profile)
}

fun NavGraphBuilder.bottomNavScreen(navController: NavHostController){
    composable(BottomNavScreens.Chat.route){ ChatScreen() }
    composable(BottomNavScreens.Profile.route){ ProfileScreen() }
}
