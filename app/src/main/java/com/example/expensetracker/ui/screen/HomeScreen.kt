@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.expensetracker.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.expensetracker.R
import com.example.expensetracker.ExpenseTrackerApplication
import com.example.expensetracker.data.model.Expense
import com.example.expensetracker.ui.Screen
import com.example.expensetracker.ui.component.BalanceCard
import com.example.expensetracker.ui.component.HomeTopBar
import com.example.expensetracker.util.formatDate
import com.example.expensetracker.viewmodel.*
import java.text.NumberFormat
import java.util.*

@Composable
fun HomeScreen(
    navController: NavHostController,
    onAddExpenseClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val app = context.applicationContext as ExpenseTrackerApplication

    val expenseViewModel: ExpenseViewModel =
        viewModel(factory = ExpenseViewModelFactory(app.expenseRepository))
    val userViewModel: UserViewModel =
        viewModel(factory = UserViewModelFactory(app.userRepository))
    val summaryViewModel: SummaryViewModel =
        viewModel(factory = SummaryViewModelFactory(app.expenseRepository))

    val username by userViewModel.username.collectAsState("")
    val allExpenses by expenseViewModel.allExpenses.collectAsState(emptyList())
    val recentTransactions = remember(allExpenses) { allExpenses.take(5) }

    val balance by summaryViewModel.balance.collectAsState()
    val income by summaryViewModel.income.collectAsState()
    val expense by summaryViewModel.expense.collectAsState()

    val categoryBreakdown = remember(allExpenses) {
        allExpenses.groupBy { it.category }
            .mapValues { it.value.sumOf { expense -> expense.amount } }
            .filterValues { it > 0 }
    }

    var showPieChart by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { HomeTopBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.AddExpense.route)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Expense")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GreetingSection(username)

            BalanceCard(balance = balance, income = income, expense = expense)

            if (categoryBreakdown.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Spending Breakdown", style = MaterialTheme.typography.titleMedium)
                            Switch(checked = showPieChart, onCheckedChange = { showPieChart = it })
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        val chartModifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)

                        if (showPieChart) {
                            CategoryPieChart(categoryData = categoryBreakdown, modifier = chartModifier)
                        } else {
                            CategoryBarChart(categoryData = categoryBreakdown, modifier = chartModifier)
                        }
                    }
                }
            }

            TransactionList(transactions = recentTransactions, navController = navController)
        }
    }
}

@Composable
fun GreetingSection(username: String) {
    val greeting = remember { getGreeting() }
    Column {
        Text(greeting, fontSize = 16.sp, color = Color.Gray)
        Text(
            "Welcome Back, ${username.ifBlank { "User" }}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

fun getGreeting(): String {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return when (hour) {
        in 5..11 -> "Good Morning"
        in 12..16 -> "Good Afternoon"
        in 17..20 -> "Good Evening"
        else -> "Good Night"
    }
}

@Composable
fun TransactionList(transactions: List<Expense>, navController: NavHostController) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Recent Transactions", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "See All",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    navController.navigate(Screen.Transactions.route)
                }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (transactions.isEmpty()) {
            Text("No recent transactions yet", color = Color.Gray, fontSize = 14.sp)
        } else {
            transactions.forEach { expense ->
                TransactionItem(
                    title = expense.title,
                    amount = NumberFormat.getCurrencyInstance(Locale.US).format(expense.amount),
                    icon = if (expense.type.lowercase() == "expense") R.drawable.ic_expense else R.drawable.ic_income,
                    date = formatDate(expense.date),
                    color = if (expense.type.lowercase() == "expense") Color.Red else Color.Green
                )
            }
        }
    }
}

@Composable
fun TransactionItem(title: String, amount: String, icon: Int, date: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.LightGray, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = title,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Text(date, fontSize = 12.sp, color = Color.Gray)
        }
        Text(amount, color = color, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}
