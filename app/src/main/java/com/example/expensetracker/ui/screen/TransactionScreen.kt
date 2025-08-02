package com.example.expensetracker.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expensetracker.data.entity.Expense
import com.example.expensetracker.viewmodel.ExpenseViewModel
import com.example.expensetracker.ui.common.DatePickerDialog
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionScreen(
    viewModel: ExpenseViewModel = viewModel()
) {
    val context = LocalContext.current

    // UI state
    var categoryFilter by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf(0L) }
    var endDate by remember { mutableStateOf(System.currentTimeMillis()) }

    var showStartPicker by remember { mutableStateOf(false) }
    var showEndPicker by remember { mutableStateOf(false) }

    // Collect the actual expenses from ViewModel
    val expenses by viewModel.expenses.collectAsState(initial = emptyList())

    // Filter on the fly
    val filteredExpenses = expenses.filter {
        (categoryFilter.isBlank() || it.category.contains(categoryFilter, ignoreCase = true)) &&
                it.date in startDate..endDate
    }

    val totalAmount = filteredExpenses.sumOf { it.amount }
    val categoryBreakdown = filteredExpenses.groupBy { it.category }
        .mapValues { entry -> entry.value.sumOf { expense -> expense.amount } }

    Column(modifier = Modifier.padding(16.dp)) {

        // --- Filters ---
        OutlinedTextField(
            value = categoryFilter,
            onValueChange = { categoryFilter = it },
            label = { Text("Filter by Category") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { showStartPicker = true }) {
                Text("Start: ${formatDate(startDate)}")
            }
            Button(onClick = { showEndPicker = true }) {
                Text("End: ${formatDate(endDate)}")
            }
        }

        if (showStartPicker) {
            DatePickerDialog(
                initialDate = startDate,
                onDateSelected = {
                    startDate = it
                    showStartPicker = false
                },
                onDismiss = { showStartPicker = false }
            )
        }

        if (showEndPicker) {
            DatePickerDialog(
                initialDate = endDate,
                onDateSelected = {
                    endDate = it
                    showEndPicker = false
                },
                onDismiss = { showEndPicker = false }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // --- Summary Card ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Summary", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Total: $${"%.2f".format(totalAmount)}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                categoryBreakdown.forEach { (category, amount) ->
                    Text("$category: $${"%.2f".format(amount)}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Expense List ---
        LazyColumn(modifier = Modifier.fillMaxHeight(0.6f)) {
            items(filteredExpenses) { expense ->
                ExpenseItem(
                    expense = expense,
                    onEdit = {
                        Toast.makeText(context, "Edit not yet implemented", Toast.LENGTH_SHORT).show()
                    },
                    onDelete = {
                        viewModel.deleteExpense(expense)
                        Toast.makeText(context, "Expense deleted", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Category Breakdown Chart (Simple Progress Bars) ---
        Text("Category Breakdown (Chart View)", style = MaterialTheme.typography.titleSmall)
        categoryBreakdown.forEach { (category, amount) ->
            val percent = if (totalAmount > 0) (amount / totalAmount * 100).roundToInt() else 0
            LinearProgressIndicator(
                progress = percent / 100f,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
            Text("$category - $percent%", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun ExpenseItem(
    expense: Expense,
    onEdit: (Expense) -> Unit,
    onDelete: (Expense) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = expense.title, style = MaterialTheme.typography.titleMedium)
                    Text(text = "$${"%.2f".format(expense.amount)}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = expense.category, style = MaterialTheme.typography.labelSmall)
                    Text(text = formatDate(expense.date), style = MaterialTheme.typography.labelSmall)
                }
                Row {
                    IconButton(onClick = { onEdit(expense) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = { onDelete(expense) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }
        }
    }
}

fun formatDate(millis: Long): String {
    return SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(millis))
}
