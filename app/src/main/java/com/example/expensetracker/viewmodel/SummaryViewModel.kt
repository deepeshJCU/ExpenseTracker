package com.example.expensetracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.repository.ExpenseRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SummaryViewModel(private val repository: ExpenseRepository) : ViewModel() {

    private val _income = MutableStateFlow(0.0)
    val income: StateFlow<Double> = _income.asStateFlow()

    private val _expense = MutableStateFlow(0.0)
    val expense: StateFlow<Double> = _expense.asStateFlow()

    val balance: StateFlow<Double> = combine(income, expense) { income, expense ->
        income - expense
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    init {
        viewModelScope.launch {
            repository.getTotalIncome().collect { _income.value = it }
        }
        viewModelScope.launch {
            repository.getTotalExpense().collect { _expense.value = it }
        }
    }
}

class SummaryViewModelFactory(private val repository: ExpenseRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SummaryViewModel(repository) as T
    }
}
