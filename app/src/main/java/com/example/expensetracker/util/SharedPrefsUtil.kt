package com.example.expensetracker.util

import android.content.Context

object Constants {
    const val PREF_NAME = "expense_tracker_prefs"
    const val KEY_DARK_THEME = "dark_theme"
    const val KEY_SPENDING_LIMIT = "spending_limit"
    const val KEY_USER_LOGGED_IN = "user_logged_in"
    const val KEY_PASSWORD = "password"
    const val KEY_EMAIL = "email"
    const val KEY_USERNAME = "username"
}

class SharedPrefsUtil(context: Context) {

    private val prefs = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)

    // Theme preference
    fun isDarkTheme(): Boolean = prefs.getBoolean(Constants.KEY_DARK_THEME, false)

    fun setDarkTheme(isDark: Boolean) {
        prefs.edit().putBoolean(Constants.KEY_DARK_THEME, isDark).apply()
    }

    // change password
    fun getUserPassword(): String {
        return prefs.getString(Constants.KEY_PASSWORD, "") ?: ""
    }

    fun getUserEmail(): String {
        return prefs.getString(Constants.KEY_EMAIL, "") ?: ""
    }

    fun getUserName(): String {
        return prefs.getString(Constants.KEY_USERNAME, "") ?: ""
    }


    fun setUserPassword(password: String) {
        prefs.edit().putString(Constants.KEY_PASSWORD, password).apply()
    }

    // Login state
    fun isUserLoggedIn(): Boolean = prefs.getBoolean(Constants.KEY_USER_LOGGED_IN, false)

    fun setUserLoggedIn(loggedIn: Boolean) {
        prefs.edit().putBoolean(Constants.KEY_USER_LOGGED_IN, loggedIn).apply()
    }

    // Spending limit
    fun getLimit(): Double = prefs.getFloat(Constants.KEY_SPENDING_LIMIT, 0f).toDouble()

    fun setLimit(value: Double) {
        prefs.edit().putFloat(Constants.KEY_SPENDING_LIMIT, value.toFloat()).apply()
    }

    //profile

    fun setProfileImage(uri: String) {
        prefs.edit().putString("profile_image_uri", uri).apply()
    }

    fun getProfileImage(): String? {
        return prefs.getString("profile_image_uri", null)
    }

    fun getUserPhone(): String? {
        return prefs.getString("user_phone", "")
    }

    fun setUserPhone(phone: String) {
        prefs.edit().putString("user_phone", phone).apply()
    }

    fun setUserName(name: String) {
        prefs.edit().putString(Constants.KEY_USERNAME, name).apply()
    }

    fun setUserEmail(email: String) {
        prefs.edit().putString(Constants.KEY_EMAIL, email).apply()
    }




    fun clearUser() {
        prefs.edit()
            .remove(Constants.KEY_USER_LOGGED_IN)
            .remove(Constants.KEY_USERNAME)
            .remove(Constants.KEY_EMAIL)
            .remove(Constants.KEY_PASSWORD)
            .apply()
    }

}
