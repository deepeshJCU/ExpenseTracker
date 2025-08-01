package com.example.expensetracker.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.*
import com.example.expensetracker.ui.navigation.Screen
import com.example.expensetracker.ui.screen.AddExpenseScreen
import com.example.expensetracker.ui.screen.HomeScreen
import com.example.expensetracker.ui.screen.SettingsScreen
import com.example.expensetracker.ui.screen.TransactionScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val items = listOf(Screen.Home, Screen.Transactions, Screen.Settings)

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        label = { Text(screen.label) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            if (currentRoute != screen.route) {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onAddExpenseClick = { navController.navigate(Screen.AddExpense.route) }
                )
            }

            composable(Screen.AddExpense.route) {
                AddExpenseScreen(onExpenseAdded = { navController.popBackStack() })
            }

            composable(Screen.Transactions.route) { TransactionScreen() }
            composable(Screen.Settings.route) { SettingsScreen() }
        }
    }
}
