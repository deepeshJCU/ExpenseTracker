package com.example.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.model.User
import com.example.expensetracker.data.repository.UserRepository
import com.example.expensetracker.util.SharedPrefsUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Success : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}


class AuthViewModel(
    private val repository: UserRepository,
    private val prefs: SharedPrefsUtil
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    fun login(username: String, password: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val user = repository.login(username, password)
                if (user != null) {
                    prefs.setUserLoggedIn(true) //Fixed
                    _authState.value = AuthState.Success
                } else {
                    _authState.value = AuthState.Error("Invalid credentials")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Login failed: ${e.message ?: "Unknown error"}")
            }
        }
    }

    fun signup(username: String, password: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val existing = repository.getUser(username)
                if (existing != null) {
                    _authState.value = AuthState.Error("User already exists")
                    return@launch
                }

                repository.register(User(username = username, password = password))
                prefs.setUserLoggedIn(true) // Fixed
                _authState.value = AuthState.Success
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Registration failed: ${e.message ?: "Unknown error"}")
            }
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}

