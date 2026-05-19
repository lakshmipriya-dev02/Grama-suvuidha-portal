package com.gramasuvidha.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.gramasuvidha.utils.LanguageManager
import com.gramasuvidha.utils.UserManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {

    val isKannada = LanguageManager.isKannada.value
    val user = UserManager.user.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (isKannada) "ಪ್ರೊಫೈಲ್" else "Profile")
                }
            )
        }
    ) { padding ->

        if (user == null) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isKannada)
                        "ದಯವಿಟ್ಟು ಮೊದಲು ನೋಂದಣಿ ಮಾಡಿ"
                    else
                        "Please sign up first"
                )
            }

        } else {

            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Avatar Circle
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = user.name.first().uppercase(),
                        fontSize = 32.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Name
                Text(
                    text = user.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))

                //  Info Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        ProfileItem("Username", user.username)
                        ProfileItem("Phone", user.phone)
                        ProfileItem("Email", user.email)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                //  Edit Button (UI only)
                OutlinedButton(onClick = { }) {
                    Text(if (isKannada) "ತಿದ್ದುಪಡಿ" else "Edit Profile")
                }
            }
        }
    }
}

@Composable
fun ProfileItem(label: String, value: String) {

    Column(modifier = Modifier.padding(vertical = 6.dp)) {

        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.Gray
        )

        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}