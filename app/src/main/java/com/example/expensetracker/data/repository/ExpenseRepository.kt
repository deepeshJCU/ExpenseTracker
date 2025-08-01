package com.example.expensetracker.data.repository

import com.example.expensetracker.data.dao.ExpenseDao
import com.example.expensetracker.data.entity.Expense

class ExpenseRepository(private val expenseDao: ExpenseDao) {
    suspend fun insert(expense: Expense) = expenseDao.insertExpense(expense)
    suspend fun delete(expense: Expense) = expenseDao.deleteExpense(expense)
    fun getAllExpenses() = expenseDao.getAllExpenses()
    fun getExpensesByType(type: String) = expenseDao.getExpensesByType(type)
    fun getTotalAmountByType(type: String) = expenseDao.getTotalAmountByType(type)
    fun getExpensesBetween(start: Long, end: Long) = expenseDao.getExpensesBetweenDates(start, end)
}
