@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.expensetracker.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.R

@Composable
fun HomeScreen(
    onAddExpenseClick: () -> Unit = {}, // For navigation to AddExpenseScreen
    onTabSelected: (Int) -> Unit = {},  // Communicate tab change if needed
) {
    var selectedItem by remember { mutableStateOf(0) }

    Scaffold(
        topBar = { HomeTopBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddExpenseClick) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(painterResource(R.drawable.ic_home), contentDescription = "Home") },
                    selected = selectedItem == 0,
                    onClick = {
                        selectedItem = 0
                        onTabSelected(0)
                    },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    icon = { Icon(painterResource(R.drawable.ic_transactions), contentDescription = "Transactions") },
                    selected = selectedItem == 1,
                    onClick = {
                        selectedItem = 1
                        onTabSelected(1)
                    },
                    label = { Text("Transactions") }
                )
                NavigationBarItem(
                    icon = { Icon(painterResource(R.drawable.ic_settings), contentDescription = "Settings") },
                    selected = selectedItem == 2,
                    onClick = {
                        selectedItem = 2
                        onTabSelected(2)
                    },
                    label = { Text("Settings") }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            GreetingSection()
            Spacer(modifier = Modifier.height(16.dp))
            BalanceCard()
            Spacer(modifier = Modifier.height(16.dp))
            TransactionList()
        }
    }
}

@Composable
fun HomeTopBar() {
    CenterAlignedTopAppBar(
        title = { Text("Expense Tracker") },
        actions = {
            IconButton(onClick = { /* TODO: Handle notifications */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = "Notifications"
                )
            }
        }
    )
}

@Composable
fun GreetingSection() {
    Column {
        Text("Good Afternoon", fontSize = 16.sp, color = Color.Gray)
        Text("Welcome Back, Deepesh", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun BalanceCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1565C0))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Total Balance", color = Color.White, fontSize = 16.sp)
                    Text("$5,000", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
                Icon(
                    painter = painterResource(id = R.drawable.dots_menu),
                    contentDescription = "Options",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Income", color = Color.White)
                    Text("$4,000", fontWeight = FontWeight.Bold, color = Color.White)
                }
                Column {
                    Text("Expense", color = Color.White)
                    Text("$3,000", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun TransactionList() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Recent Transactions", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("See All", fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
        }
        Spacer(modifier = Modifier.height(8.dp))
        TransactionItem("Netflix", "$130", R.drawable.ic_netflix, "Today", Color.Red)
        TransactionItem("PayPal", "$240", R.drawable.ic_paypal, "Yesterday", Color.Green)
        TransactionItem("YouTube", "$99", R.drawable.ic_youtube, "Today", Color.Red)
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
        Image(
            painter = painterResource(id = icon),
            contentDescription = title,
            modifier = Modifier
                .size(40.dp)
                .background(Color.LightGray, shape = CircleShape)
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Text(date, fontSize = 12.sp, color = Color.Gray)
        }
        Text(amount, color = color, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
