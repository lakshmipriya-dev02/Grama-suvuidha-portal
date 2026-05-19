package com.gramasuvidha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.gramasuvidha.ui.AppNavigation
import com.gramasuvidha.ui.theme.GramaSuvidhaTheme
import com.gramasuvidha.utils.UserManager

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔥 LOAD USER WHEN APP STARTS
        UserManager.loadUser(this)

        setContent {
            GramaSuvidhaTheme {
                AppNavigation()
            }
        }
    }
}