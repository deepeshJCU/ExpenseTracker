package com.example.expensetracker.util

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(millis: Long): String {
    return try {
        val sdf = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
        sdf.format(Date(millis))
    } catch (e: Exception) {
        ""
    }
}
