package com.example.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.data.db.AppDatabase
import com.example.expensetracker.data.repository.UserRepository
import com.example.expensetracker.ui.MainScreen
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import com.example.expensetracker.util.SharedPrefsUtil
import com.example.expensetracker.viewmodel.AuthViewModel
import com.example.expensetracker.viewmodel.AuthViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val context = applicationContext
            val prefs = SharedPrefsUtil(context)
            var isDarkTheme by remember { mutableStateOf(prefs.isDarkTheme()) }

            // Manual ViewModel creation
            val userDao = AppDatabase.getDatabase(context).userDao()
            val userRepository = UserRepository(userDao, context)
            val authViewModel: AuthViewModel = ViewModelProvider(
                this,
                AuthViewModelFactory(userRepository, prefs)
            )[AuthViewModel::class.java]

            ExpenseTrackerTheme(useDarkTheme = isDarkTheme) {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        navController = navController,
                        onToggleTheme = {
                            isDarkTheme = !isDarkTheme
                            prefs.setDarkTheme(isDarkTheme)
                        },
                        isDark = isDarkTheme,
                        authViewModel = authViewModel // <- manually passed
                    )
                }
            }
        }
    }
}
