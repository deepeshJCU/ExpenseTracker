package com.example.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.repository.UserRepository
import kotlinx.coroutines.flow.*

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    // Assuming repository.username is a Flow<String>
    val username: StateFlow<String> = repository.username
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )
}
