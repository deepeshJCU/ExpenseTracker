package com.example.expensetracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Double,
    val category: String,
    val description: String = "",
    val date: Long, // Store as timestamp (Epoch millis)
    val type: String // "income" or "expense"
)
