package com.example.expensetracker.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

// Use this for navigation and bottom bar consistency
sealed class Screen(val route: String, val label: String = "", val icon: ImageVector? = null) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Signup : Screen("signup")

    object Home : Screen("home", "Home", Icons.Default.Home)
    object Transactions : Screen("transactions", "Transactions", Icons.Default.List)
    object Settings : Screen("settings", "Settings", Icons.Default.Settings)
    object AddExpense : Screen("add_expense", "Add Expense", Icons.Default.Add)
    object Profile : Screen("profile_view", "Profile", Icons.Default.Person)

    data class EditExpense(val expenseId: Int) : Screen("edit_expense/$expenseId")
}
