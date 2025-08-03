package com.example.expensetracker

import android.app.Application
import com.example.expensetracker.data.db.AppDatabase
import com.example.expensetracker.data.repository.ExpenseRepository

class ExpenseTrackerApplication : Application() {
    // Public access for ViewModelFactory
    lateinit var repository: ExpenseRepository
        private set

    override fun onCreate() {
        super.onCreate()
        val database = AppDatabase.getDatabase(this)
        repository = ExpenseRepository(database.expenseDao())
    }
}
