package com.example.expensetracker.ui.screen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.expensetracker.R
import com.example.expensetracker.util.SharedPrefsUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileViewScreen(
    navController: NavHostController,
    prefs: SharedPrefsUtil = SharedPrefsUtil(LocalContext.current)
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(Unit) {
        name = prefs.getUserName()
        email = prefs.getUserEmail()
        phone = prefs.getUserPhone() ?: ""
        imageUri = prefs.getProfileImage()?.let { Uri.parse(it) }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = name, style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = email,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Card(
                shape = CircleShape,
                modifier = Modifier.size(120.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Image(
                    painter = imageUri?.let { rememberAsyncImagePainter(model = it) }
                        ?: painterResource(id = R.drawable.ic_profile_placeholder),
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Profile Info", style = MaterialTheme.typography.headlineSmall)

                    ProfileField(label = "👤 Name", value = name)
                    ProfileField(label = "📧 Email", value = email)
                    ProfileField(label = "📱 Phone", value = phone)

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { navController.navigate("edit_profile") },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Edit Profile")
                    }
                }
            }
        }
    }
}
@Composable
fun ProfileField(label: String, value: String?) {
    Text(
        text = "$label: ${value ?: "N/A"}",
        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
    )
}
