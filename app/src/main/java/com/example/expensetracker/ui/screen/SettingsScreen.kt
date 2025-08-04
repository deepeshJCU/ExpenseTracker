package com.example.expensetracker.ui.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.expensetracker.util.SharedPrefsUtil

@Composable
fun SettingsScreen(
    navController: NavHostController,
    isDark: Boolean,
    onToggleTheme: () -> Unit
) {
    val context = LocalContext.current
    val prefs = remember { SharedPrefsUtil(context) }

    var showLogoutDialog by remember { mutableStateOf(false) }
    var showAbout by remember { mutableStateOf(false) }
    var limitInput by remember { mutableStateOf(prefs.getLimit().toString()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("Settings", style = MaterialTheme.typography.headlineMedium)

        // Theme Toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.DarkMode, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Dark Mode", style = MaterialTheme.typography.bodyLarge)
            }
            Switch(
                checked = isDark,
                onCheckedChange = { onToggleTheme() }
            )
        }

        // Notifications
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.navigate("notifications") },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(8.dp))
            Text("View Notifications", style = MaterialTheme.typography.bodyLarge)
        }

        // Spending Limit
        OutlinedTextField(
            value = limitInput,
            onValueChange = { limitInput = it },
            label = { Text("Spending Limit (₦)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                val limitValue = limitInput.toDoubleOrNull()
                if (limitValue != null && limitValue > 0) {
                    prefs.setLimit(limitValue)
                    Toast.makeText(context, "Spending limit saved", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Enter a valid number", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Limit")
        }

        // Profile
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.navigate("profile") },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Person, contentDescription = "Profile", tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Profile", style = MaterialTheme.typography.bodyLarge)
        }

        // About
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showAbout = true },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Info, contentDescription = "About", tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(8.dp))
            Text("About", style = MaterialTheme.typography.bodyLarge)
        }

        // Logout
        Button(
            onClick = { showLogoutDialog = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Icon(Icons.Default.Logout, contentDescription = "Logout", tint = MaterialTheme.colorScheme.onError)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Logout", color = MaterialTheme.colorScheme.onError)
        }
    }

    // Logout confirmation dialog
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Logout Confirmation") },
            text = { Text("Are you sure you want to logout?") },
            confirmButton = {
                TextButton(onClick = {
                    showLogoutDialog = false
                    prefs.clearUser()
                    Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }) {
                    Text("Logout")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // About dialog
    if (showAbout) {
        AlertDialog(
            onDismissRequest = { showAbout = false },
            title = { Text("About") },
            text = {
                Text("ExpenseTracker v1.0\nDeveloped by Deepesh Bijarnia\nCP5307 Assignment 1.")
            },
            confirmButton = {
                TextButton(onClick = { showAbout = false }) {
                    Text("Close")
                }
            }
        )
    }
}
