package com.example.expensetracker

import android.app.Application
import com.example.expensetracker.data.db.AppDatabase
import com.example.expensetracker.data.repository.ExpenseRepository
import com.example.expensetracker.data.repository.UserRepository


class ExpenseTrackerApplication : Application() {

    // Repositories accessible globally
    lateinit var expenseRepository: ExpenseRepository
        private set

    lateinit var userRepository: UserRepository
        private set

    override fun onCreate() {
        super.onCreate()

        // Get database instance
        val database = AppDatabase.getDatabase(this)

        // Create repository instances
        expenseRepository = ExpenseRepository(database.expenseDao())

        // Pass context to UserRepository constructor
        userRepository = UserRepository(database.userDao(), this)
    }
}
