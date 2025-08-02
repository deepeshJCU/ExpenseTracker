@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.expensetracker

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen() {
    var selectedItem by remember { mutableStateOf(0) }

    Scaffold(
        topBar = { HomeTopBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // TODO: navigate to AddExpense screen
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(painterResource(R.drawable.ic_home), contentDescription = "Home") },
                    selected = selectedItem == 0,
                    onClick = { selectedItem = 0 },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    icon = { Icon(painterResource(R.drawable.ic_transactions), contentDescription = "Transactions") },
                    selected = selectedItem == 1,
                    onClick = { selectedItem = 1 },
                    label = { Text("Transactions") }
                )
                NavigationBarItem(
                    icon = { Icon(painterResource(R.drawable.ic_settings), contentDescription = "Settings") },
                    selected = selectedItem == 2,
                    onClick = { selectedItem = 2 },
                    label = { Text("Settings") }
                )
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
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
    )
}

@Composable
fun HomeTopBar() {
    CenterAlignedTopAppBar(
        title = { Text("Expense Tracker") },
        actions = {
            IconButton(onClick = { /* TODO: Notifications */ }) {
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
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
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

@Composable
fun BottomNavigationBar() {
    NavigationBar {
        NavigationBarItem(
            selected = true,
            onClick = { },
            icon = { Icon(painterResource(id = R.drawable.ic_home), contentDescription = "Home") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(painterResource(id = R.drawable.ic_chart), contentDescription = "Chart") },
            label = { Text("Chart") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(painterResource(id = R.drawable.ic_settings), contentDescription = "Settings") },
            label = { Text("Settings") }
        )
    }
}
