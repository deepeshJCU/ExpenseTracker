package com.example.expensetracker.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.expensetracker.ExpenseTrackerApplication
import com.example.expensetracker.viewmodel.ExpenseViewModel
import com.example.expensetracker.viewmodel.ExpenseViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditExpenseScreen(
    navController: NavController,
    expenseId: Int
) {
    val context = LocalContext.current
    val application = context.applicationContext as ExpenseTrackerApplication
    val factory = ExpenseViewModelFactory(application.repository)
    val viewModel: ExpenseViewModel = viewModel(factory = factory)

    val allExpenses by viewModel.allExpenses.collectAsState(initial = emptyList())
    val expense = allExpenses.find { it.id == expenseId }

    var title by remember { mutableStateOf(expense?.title ?: "") }
    var amount by remember { mutableStateOf(expense?.amount?.toString() ?: "") }
    var category by remember { mutableStateOf(expense?.category ?: "") }

    if (expense == null) {
        Text("Expense not found.")
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Edit Expense") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val updatedExpense = expense.copy(
                        title = title,
                        amount = amount.toDoubleOrNull() ?: 0.0,
                        category = category
                    )
                    viewModel.updateExpense(updatedExpense)
                    Toast.makeText(context, "Expense updated", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Save Changes")
            }
        }
    }
}
