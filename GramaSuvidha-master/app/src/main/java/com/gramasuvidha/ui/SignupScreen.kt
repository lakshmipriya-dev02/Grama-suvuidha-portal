package com.gramasuvidha.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.gramasuvidha.data.User
import com.gramasuvidha.utils.LanguageManager
import com.gramasuvidha.utils.UserManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(navController: NavController) {

    val isKannada = LanguageManager.isKannada.value
    val context = LocalContext.current   // 🔥 IMPORTANT

    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isKannada) "ನೋಂದಣಿ" else "Sign Up") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(if (isKannada) "ಹೆಸರು" else "Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text(if (isKannada) "ಬಳಕೆದಾರ ಹೆಸರು" else "Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text(if (isKannada) "ಫೋನ್" else "Phone") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(if (isKannada) "ಇಮೇಲ್" else "Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val newUser = User(name, username, phone, email)

                    // 🔥 SAVE PERMANENTLY
                    UserManager.saveUser(context, newUser)

                    navController.navigate("profile")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isKannada) "ಸೈನ್ ಅಪ್" else "Sign Up")
            }
        }
    }
}