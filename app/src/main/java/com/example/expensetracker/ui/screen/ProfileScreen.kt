package com.example.expensetracker.ui.screen

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.expensetracker.R
import com.example.expensetracker.util.SharedPrefsUtil
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll


@Composable
fun ProfileScreen(
    navController: NavHostController,
    prefs: SharedPrefsUtil = SharedPrefsUtil(LocalContext.current)
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Load saved values when screen is first composed
    LaunchedEffect(Unit) {
        name = prefs.getUserName()
        email = prefs.getUserEmail()
        phone = prefs.getUserPhone() ?: ""
        imageUri = prefs.getProfileImage()?.toUri()
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imageUri = it
            prefs.setProfileImage(it.toString())
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Profile Image Card
        Card(
            shape = CircleShape,
            modifier = Modifier
                .size(120.dp)
                .clickable { imagePickerLauncher.launch("image/*") },
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Image(
                painter = imageUri?.let { rememberAsyncImagePainter(model = it) }
                    ?: painterResource(id = R.drawable.ic_profile_placeholder),
                contentDescription = "Profile Image",
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
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Edit Profile", style = MaterialTheme.typography.headlineSmall)

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        prefs.setUserName(name)
                        prefs.setUserEmail(email)
                        prefs.setUserPhone(phone)
                        Toast.makeText(context, "Profile Updated", Toast.LENGTH_SHORT).show()
                        navController.popBackStack() // Navigate back to view screen after save
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Changes")
                }

                Button(
                    onClick = { navController.navigate("change_password") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Change Password")
                }

                OutlinedButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Back")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
