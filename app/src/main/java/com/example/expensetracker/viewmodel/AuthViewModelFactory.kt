package com.example.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expensetracker.data.repository.UserRepository
import com.example.expensetracker.util.SharedPrefsUtil

class AuthViewModelFactory(
    private val repository: UserRepository,
    private val prefs: SharedPrefsUtil
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(repository, prefs) as T
    }
}
