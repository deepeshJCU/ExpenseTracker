package com.example.expensetracker.ui.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import androidx.lint.kotlin.metadata.Visibility
import java.text.NumberFormat
import java.util.*

@Composable
fun BalanceCard(balance: Double, income: Double, expense: Double) {
    Log.d("BalanceCard", "balance: $balance, income: $income, expense: $expense")

    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US)
    val isDataAvailable = balance != 0.0 || income != 0.0 || expense != 0.0
    var showFigures by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        // Fill card with gradient background
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF1565C0), Color(0xFF70B6F3))
                    )
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Total Balance", color = Color.White, fontSize = 16.sp)
                    Text(
                        text = if (showFigures) currencyFormatter.format(balance) else "****",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                IconButton(onClick = { showFigures = !showFigures }) {
                    Icon(
                        imageVector = if (showFigures) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle visibility",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (!isDataAvailable) {
                Text(
                    text = "No financial data yet",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Income", color = Color.Green)
                        Text(
                            text = if (showFigures) currencyFormatter.format(income) else "****",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Column {
                        Text("Expense", color = Color.Black)
                        Text(
                            text = if (showFigures) currencyFormatter.format(expense) else "****",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
