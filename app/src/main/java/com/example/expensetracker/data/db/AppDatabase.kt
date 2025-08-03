package com.example.expensetracker.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.expensetracker.data.dao.ExpenseDao
import com.example.expensetracker.data.model.Expense


@Database(entities = [Expense::class], version = 2) // updated version
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "expense_tracker_db"
                )
                    .fallbackToDestructiveMigration() // drop & rebuild on version change
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
