package com.gramasuvidha.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// 🌞 Light Colors
private val LightColors = lightColorScheme(
    primary = Color(0xFF6C5CE7),
    secondary = Color(0xFFA29BFE),
    background = Color(0xFFF5F5F5),
    surface = Color.White,
    onPrimary = Color.White,
    onBackground = Color.Black
)

// 🌙 Dark Colors
private val DarkColors = darkColorScheme(
    primary = Color(0xFFA29BFE),
    secondary = Color(0xFF6C5CE7),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.Black,
    onBackground = Color.White
)

@Composable
fun GramaSuvidhaTheme(
    content: @Composable () -> Unit
) {
    val darkTheme = isSystemInDarkTheme()

    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography(),
        content = content
    )
}