package com.example.expensetracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.expensetracker.ui.screen.*
import com.example.expensetracker.ui.Screen
import com.example.expensetracker.viewmodel.AuthViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    isDark: Boolean,
    onToggleTheme: () -> Unit,
    authViewModel: AuthViewModel // <-- manually passed
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }

        composable(Screen.Login.route) {
            LoginScreen(navController, authViewModel)
        }

        composable(Screen.Signup.route) {
            SignupScreen(navController, authViewModel)
        }

        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(Screen.AddExpense.route) {
            AddExpenseScreen(onExpenseSaved = { navController.popBackStack() })
        }

        composable(Screen.Transactions.route) {
            TransactionScreen(navController)
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                navController = navController,
                isDark = isDark,
                onToggleTheme = onToggleTheme
            )
        }

        composable("notifications") {
            NotificationScreen()
        }

        composable("profile") {
            ProfileScreen(navController)
        }

        composable("change_password") {
            ChangePasswordScreen(navController)
        }

        composable("profile_view") { ProfileViewScreen(navController) }
        composable("edit_profile") { ProfileScreen(navController) }


        composable("edit_expense/{expenseId}") { backStackEntry ->
            val expenseId = backStackEntry.arguments?.getString("expenseId")?.toIntOrNull()
            if (expenseId != null) {
                EditExpenseScreen(navController, expenseId)
            }
        }
    }
}
