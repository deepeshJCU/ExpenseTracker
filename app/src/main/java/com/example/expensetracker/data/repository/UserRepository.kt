package com.example.expensetracker.data.repository

import android.content.Context
import com.example.expensetracker.data.dao.UserDao
import com.example.expensetracker.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserRepository(
    private val userDao: UserDao,
    private val context: Context
) {

    private val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    private val _username = MutableStateFlow(prefs.getString("username", "User") ?: "User")
    val username: StateFlow<String> = _username

    suspend fun register(user: User) = userDao.insertUser(user)

    suspend fun login(username: String, password: String): User? {
        val user = userDao.login(username, password)
        if (user != null) {
            saveUsername(user.username)
        }
        return user
    }

    suspend fun getUser(username: String): User? = userDao.getUserByUsername(username)

    fun saveUsername(newUsername: String) {
        prefs.edit().putString("username", newUsername).apply()
        _username.value = newUsername
    }

    fun logout() {
        prefs.edit().remove("username").apply()
        _username.value = "User"
    }
}
