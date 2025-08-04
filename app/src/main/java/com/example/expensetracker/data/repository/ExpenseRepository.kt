package com.example.expensetracker.data.repository

import com.example.expensetracker.data.dao.ExpenseDao
import com.example.expensetracker.data.model.Expense
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExpenseRepository(
    private val expenseDao: ExpenseDao
) {

    suspend fun insertExpense(expense: Expense) {
        expenseDao.insertExpense(expense)
    }

    suspend fun deleteExpense(expense: Expense) {
        expenseDao.deleteExpense(expense)
    }

    suspend fun updateExpense(expense: Expense) {
        expenseDao.updateExpense(expense)
    }
    suspend fun addExpense(expense: Expense) = expenseDao.insertExpense(expense)

    fun getAllExpenses() = expenseDao.getAllExpenses()

    fun getTotalIncome(): Flow<Double> = expenseDao.getTotalIncome().map { it ?: 0.0 }

    fun getTotalExpense(): Flow<Double> = expenseDao.getTotalExpense().map { it ?: 0.0 }


//    fun getAllExpenses(): Flow<List<Expense>> =
//        expenseDao.getAllExpenses()

    fun getExpensesByType(type: String): Flow<List<Expense>> =
        expenseDao.getExpensesByType(type)

    fun getTotalAmountByType(type: String): Flow<Double> =
        expenseDao.getTotalAmountByType(type).map { it ?: 0.0 }

    suspend fun getTotalAmountByTypeNow(type: String): Double {
        return expenseDao.getTotalAmountByTypeNow(type) ?: 0.0
    }


    fun getExpensesBetween(start: Long, end: Long): Flow<List<Expense>> =
        expenseDao.getExpensesBetweenDates(start, end)

    fun getFilteredExpenses(category: String?, startDate: Long, endDate: Long): Flow<List<Expense>> =
        expenseDao.getFilteredExpenses(category, startDate, endDate)
}
