package com.example.expensetracker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.expensetracker.data.entity.Expense
import com.example.expensetracker.data.dao.ExpenseDao

@Database(entities = [Expense::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}
