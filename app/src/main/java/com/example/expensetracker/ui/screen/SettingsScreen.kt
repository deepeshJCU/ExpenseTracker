package com.example.expensetracker.ui.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.expensetracker.util.SharedPrefsUtil

@Composable
fun SettingsScreen(navController: NavHostController) {
    val context = LocalContext.current
    val prefs = remember { SharedPrefsUtil(context) }

    var isDarkTheme by remember { mutableStateOf(prefs.isDarkTheme()) }
    var showAbout by remember { mutableStateOf(false) }
    var limitInput by remember { mutableStateOf(prefs.getLimit().toString()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Settings", style = MaterialTheme.typography.headlineMedium)

        // 1. Theme Mode Toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Dark Mode")
            Switch(
                checked = isDarkTheme,
                onCheckedChange = {
                    isDarkTheme = it
                    prefs.setDarkTheme(it)
                    Toast.makeText(context, "Theme updated. Restart app to apply.", Toast.LENGTH_SHORT).show()
                }
            )
        }

        // 2. About
        Text(
            "About",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showAbout = true },
            style = MaterialTheme.typography.bodyLarge
        )

        // 3. Set Limit Notification
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

        // 4. Logout
        Button(
            onClick = {
                prefs.clearUser()
                navController.navigate("login") {
                    popUpTo(0) // Clear backstack
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Logout", color = MaterialTheme.colorScheme.onError)
        }
    }

    if (showAbout) {
        AlertDialog(
            onDismissRequest = { showAbout = false },
            title = { Text("About") },
            text = {
                Text("ExpenseTracker v1.0\nDeveloped by Akahal Johnson.\nCP5307 Assignment 1.")
            },
            confirmButton = {
                TextButton(onClick = { showAbout = false }) {
                    Text("Close")
                }
            }
        )
    }
}
