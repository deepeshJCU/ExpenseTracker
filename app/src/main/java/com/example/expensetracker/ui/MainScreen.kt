package com.example.expensetracker.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.expensetracker.ui.component.BottomBar
import com.example.expensetracker.ui.navigation.NavGraph
import com.example.expensetracker.ui.Screen
import com.example.expensetracker.viewmodel.AuthViewModel

@Composable
fun MainScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onToggleTheme: () -> Unit,
    isDark: Boolean,
    authViewModel: AuthViewModel // <--- add this
) {
    val hideBottomNavRoutes = listOf(
        Screen.Splash.route,
        Screen.Login.route,
        Screen.Signup.route
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute !in hideBottomNavRoutes) {
                BottomBar(navController)
            }
        }
    ) { padding ->
        NavGraph(
            navController = navController,
            modifier = modifier.padding(padding),
            onToggleTheme = onToggleTheme,
            isDark = isDark,
            authViewModel = authViewModel // <--- pass it here
        )
    }
}
