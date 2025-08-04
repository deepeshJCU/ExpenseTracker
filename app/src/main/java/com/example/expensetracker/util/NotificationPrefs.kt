package com.example.expensetracker.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NotificationPrefs(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("notifications", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val key = "notification_list"

    fun getNotifications(): List<String> {
        val json = prefs.getString(key, null)
        return if (!json.isNullOrEmpty()) {
            try {
                val type = object : TypeToken<List<String>>() {}.type
                gson.fromJson(json, type)
            } catch (e: Exception) {
                emptyList() // fallback if malformed data
            }
        } else {
            emptyList()
        }
    }

    fun addNotification(message: String) {
        val currentList = getNotifications().toMutableList()
        currentList.add(0, message) // Add newest first
        if (currentList.size > 10) {
            currentList.subList(10, currentList.size).clear() // Keep only the latest 10
        }
        prefs.edit().putString(key, gson.toJson(currentList)).apply()
    }

    fun clearNotifications() {
        prefs.edit().remove(key).apply()
    }
}
