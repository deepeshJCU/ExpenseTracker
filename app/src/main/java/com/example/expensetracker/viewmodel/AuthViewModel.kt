package com.example.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.model.User
import com.example.expensetracker.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel(private val repository: UserRepository) : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val user = repository.login(username, password)
            _authState.value = if (user != null) AuthState.Success else AuthState.Error("Invalid credentials")
        }
    }

    fun signup(username: String, password: String) {
        viewModelScope.launch {
            val existing = repository.getUser(username)
            if (existing != null) {
                _authState.value = AuthState.Error("User already exists")
                return@launch
            }

            try {
                repository.register(User(username = username, password = password))
                _authState.value = AuthState.Success
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Registration failed")
            }
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}
