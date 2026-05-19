package com.gramasuvidha.utils

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.gramasuvidha.data.User

object UserManager {

    var user = mutableStateOf<User?>(null)

    fun saveUser(context: Context, userData: User) {
        val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        prefs.edit().apply {
            putString("name", userData.name)
            putString("username", userData.username)
            putString("phone", userData.phone)
            putString("email", userData.email)
            apply()
        }

        user.value = userData
    }

    fun loadUser(context: Context) {
        val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        val name = prefs.getString("name", null)

        if (name != null) {
            user.value = User(
                name = name,
                username = prefs.getString("username", "") ?: "",
                phone = prefs.getString("phone", "") ?: "",
                email = prefs.getString("email", "") ?: ""
            )
        }
    }

    fun logout(context: Context) {
        val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
        user.value = null
    }
}