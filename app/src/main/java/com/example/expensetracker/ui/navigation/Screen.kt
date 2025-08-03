import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

object Screen {
    val Home = ScreenItem("home", "Home", Icons.Default.Home)
    val Transactions = ScreenItem("transactions", "Transactions", Icons.Default.List)
    val Settings = ScreenItem("settings", "Settings", Icons.Default.Settings)
    val AddExpense = ScreenItem("add_expense", "Add Expense", Icons.Default.Add)
}

data class ScreenItem(val route: String, val label: String, val icon: ImageVector)

