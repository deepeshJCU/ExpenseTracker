package com.example.expensetracker.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expensetracker.data.db.AppDatabase
import com.example.expensetracker.data.model.Expense
import com.example.expensetracker.data.repository.ExpenseRepository
import com.example.expensetracker.util.NotificationPrefs
import com.example.expensetracker.util.sendTransactionNotification
import com.example.expensetracker.viewmodel.ExpenseViewModel
import com.example.expensetracker.viewmodel.ExpenseViewModelFactory

private const val DISPLAY_EXPENSE = "Expense"
private const val DISPLAY_INCOME = "Income"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    onExpenseSaved: () -> Unit = {}
) {
    val context = LocalContext.current

    val prefs = remember { com.example.expensetracker.util.SharedPrefsUtil(context) }
    val notificationPrefs = remember { NotificationPrefs(context) }

    val viewModel: ExpenseViewModel = viewModel(
        factory = ExpenseViewModelFactory(
            ExpenseRepository(
                AppDatabase.getDatabase(context).expenseDao()
            )
        )
    )

    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(DISPLAY_EXPENSE) }
    val typeOptions = listOf(DISPLAY_EXPENSE, DISPLAY_INCOME)
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text("Add Expense", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Category") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description (optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                value = type,
                onValueChange = {},
                readOnly = true,
                label = { Text("Type") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                typeOptions.forEach {
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            type = it
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (title.isNotBlank() && amount.isNotBlank() && category.isNotBlank()) {
                    val parsedAmount = amount.toDoubleOrNull()
                    if (parsedAmount == null || parsedAmount <= 0.0) {
                        Toast.makeText(context, "Enter a valid amount", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val lowercaseType = type.lowercase()

                    viewModel.addExpenseWithLimitCheck(
                        context,
                        Expense(
                            title = title.trim(),
                            amount = parsedAmount,
                            category = category.trim(),
                            description = description.trim().takeIf { it.isNotBlank() },
                            type = lowercaseType,
                            date = System.currentTimeMillis()
                        ),
                        prefs.getLimit()
                    )

                    // Save to shared notification store
                    notificationPrefs.addNotification("You added: $title - ₦$amount")

                    Toast.makeText(context, "Expense saved", Toast.LENGTH_SHORT).show()

                    // Trigger system notification
                    sendTransactionNotification(
                        context = context,
                        title = "Transaction Added",
                        message = "You added: $title - ₦$amount"
                    )

                    onExpenseSaved()

                    // Reset form
                    title = ""
                    amount = ""
                    category = ""
                    description = ""
                    type = DISPLAY_EXPENSE
                } else {
                    Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Expense")
        }
    }
}