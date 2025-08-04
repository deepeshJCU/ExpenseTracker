package com.example.expensetracker.util

import android.content.Context
import androidx.work.*
import com.example.expensetracker.worker.NotificationWorker
import java.util.concurrent.TimeUnit

fun sendTransactionNotification(context: Context, title: String, message: String) {
    val data = Data.Builder()
        .putString("title", title)
        .putString("message", message)
        .build()

    val request = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInputData(data)
        .build()

    WorkManager.getInstance(context).enqueue(request)
}
