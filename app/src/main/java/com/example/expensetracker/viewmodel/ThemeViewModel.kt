package com.example.expensetracker.viewmodel

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.expensetracker.util.SharedPrefsUtil

class ThemeViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = SharedPrefsUtil(application)

    // Reactive state to reflect theme changes in Compose
    var isDarkTheme by mutableStateOf(prefs.isDarkTheme())
        private set

    init {
        applyTheme(isDarkTheme)
    }

    fun toggleTheme() {
        isDarkTheme = !isDarkTheme
        prefs.setDarkTheme(isDarkTheme)
        applyTheme(isDarkTheme)
    }

    private fun applyTheme(dark: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (dark) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }
}
