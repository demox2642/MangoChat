package com.example.mangochat

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mangochat.ui.theme.BottomNavScreens
import com.example.presentation.AuthScreens
import com.example.presentation.authScreens
import com.example.presentation.chatScreens
import com.example.presentation.profileScreens
import com.example.presentation.theme.AppTheme
import com.example.presentation.theme.AppTheme.AppTheme
import com.example.presentation.theme.appDarkColors
import com.example.presentation.theme.appLightColors
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProvideWindowInsets {
                this.window.statusBarColor = ContextCompat.getColor(this, R.color.black)
                val darkTheme: Boolean = isSystemInDarkTheme()
                val colors = if (darkTheme) appDarkColors() else appLightColors()
                Log.e("Theme", "darkTheme=$darkTheme")
                AppTheme(colors = colors) {
                    SystemUi(windows = window)
                    Surface(color = MaterialTheme.colors.background) {
                        MainContent()
                    }
                }
            }
        }
    }
}

@Composable
fun SystemUi(windows: Window) =
    AppTheme {
        windows.statusBarColor = AppTheme.colors.systemBackgroundPrimary.toArgb()
        windows.navigationBarColor = AppTheme.colors.systemBackgroundPrimary.toArgb()

        @Suppress("DEPRECATION")
        if (AppTheme.colors.systemBackgroundPrimary.luminance() > 0.5f) {
            windows.decorView.systemUiVisibility = windows.decorView.systemUiVisibility or
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        @Suppress("DEPRECATION")
        if (AppTheme.colors.systemBackgroundPrimary.luminance() > 0.5f) {
            windows.decorView.systemUiVisibility = windows.decorView.systemUiVisibility or
                View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
    }

val showNavBar =
    listOf(
        BottomNavScreens.Chat,
        BottomNavScreens.Profile,
    ).map { it.route }

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MainContent() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNav(navController) },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AuthScreens.PhoneScreen.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            authScreens(navController)
            chatScreens(navController)
            profileScreens(navController)
        }
    }
}

@Composable
fun BottomNav(navController: NavHostController) {
    val items =
        listOf(
            BottomNavScreens.Chat,
            BottomNavScreens.Profile,
        )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    if (currentDestination?.route in showNavBar) {
        BottomNavigation(
            backgroundColor = AppTheme.colors.systemBackgroundPrimary,
            modifier =
                Modifier
                    .defaultMinSize(minWidth = 1.dp),
            elevation = 0.dp,
        ) {
            items.forEach { screen ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painterResource(screen.icon),
                            contentDescription = null,
                        )
                    },
                    modifier =
                        Modifier.then(
                            Modifier.weight(
                                if (stringResource(screen.nameId).length > 7) {
                                    1.3f
                                } else {
                                    if (stringResource(screen.nameId).length < 5) {
                                        0.7f
                                    } else {
                                        1f
                                    }
                                },
                            ),
                        ),
                    // change label size
                    label = { Text(stringResource(screen.nameId), maxLines = 1, softWrap = true) },
                    selectedContentColor = AppTheme.colors.controlGraphBlue,
                    unselectedContentColor = AppTheme.colors.systemGraphSecondary,
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    onClick = {
                        navController.navigate(screen.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }
        }
    }
}
