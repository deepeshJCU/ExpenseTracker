package com.example.expensetracker.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val icon: ImageVector, val label: String) {
    object Home : Screen("home", Icons.Default.Home, "Home")
    object Transactions : Screen("transactions", Icons.Default.List, "Transactions")
    object Settings : Screen("settings", Icons.Default.Settings, "Settings")
    object AddExpense : Screen("add_expense", "Add Expense", Icons.Default.Add)
}
