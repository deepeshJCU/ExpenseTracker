package com.example.expensetracker.data.dao

import androidx.room.*
import com.example.expensetracker.data.model.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense)

    @Delete
    suspend fun deleteExpense(expense: Expense)

    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<Expense>>

    @Query("SELECT * FROM expenses WHERE type = :type ORDER BY date DESC")
    fun getExpensesByType(type: String): Flow<List<Expense>>

    @Query("SELECT SUM(amount) FROM expenses WHERE type = :type")
    fun getTotalAmountByType(type: String): Flow<Double?>

    @Query("SELECT * FROM expenses WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getExpensesBetweenDates(startDate: Long, endDate: Long): Flow<List<Expense>>

    @Query("SELECT * FROM expenses WHERE (:category IS NULL OR category = :category) AND date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getFilteredExpenses(category: String?, startDate: Long, endDate: Long): Flow<List<Expense>>
}
