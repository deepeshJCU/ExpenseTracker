package com.example.expensetracker.data.repository

import com.example.expensetracker.data.dao.ExpenseDao
import com.example.expensetracker.data.entity.Expense
import kotlinx.coroutines.flow.Flow

class ExpenseRepository(private val expenseDao: ExpenseDao) {

    suspend fun insert(expense: Expense) {
        expenseDao.insertExpense(expense)
    }

    suspend fun delete(expense: Expense) {
        expenseDao.deleteExpense(expense)
    }

    fun getAllExpenses(): Flow<List<Expense>> {
        return expenseDao.getAllExpenses()
    }

    fun getExpensesByType(type: String): Flow<List<Expense>> {
        return expenseDao.getExpensesByType(type)
    }

    fun getTotalAmountByType(type: String): Flow<Double> {
        return expenseDao.getTotalAmountByType(type)
    }

    fun getExpensesBetween(start: Long, end: Long): Flow<List<Expense>> {
        return expenseDao.getExpensesBetweenDates(start, end)
    }
}
