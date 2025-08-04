@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.expensetracker.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.expensetracker.util.NotificationPrefs

@Composable
fun HomeTopBar() {
    val context = LocalContext.current
    val notificationPrefs = remember { NotificationPrefs(context) }

    var showDialog by remember { mutableStateOf(false) }
    val notifications = remember { mutableStateListOf<String>() }

    // Load notifications on first composition
    LaunchedEffect(Unit) {
        notifications.clear()
        notifications.addAll(notificationPrefs.getNotifications())
    }

    CenterAlignedTopAppBar(
        title = { Text("Expense Tracker") },
        actions = {
            BadgedBox(
                modifier = Modifier.padding(end = 8.dp),
                badge = {
                    if (notifications.isNotEmpty()) {
                        Badge { Text("${notifications.size}") }
                    }
                }
            ) {
                IconButton(onClick = { showDialog = true }) {
                    Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                }
            }
        }
    )


    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Notifications") },
            text = {
                if (notifications.isEmpty()) {
                    Text("No notifications.")
                } else {
                    Column {
                        notifications.forEachIndexed { index, note ->
                            Text("• $note")
                            if (index != notifications.lastIndex) {
                                Divider(modifier = Modifier.padding(vertical = 4.dp))
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Close")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    notificationPrefs.clearNotifications()
                    notifications.clear()
                }) {
                    Text("Clear All")
                }
            }
        )
    }
}
