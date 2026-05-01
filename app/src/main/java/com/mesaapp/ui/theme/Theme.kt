package com.mesaapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ColorScheme = darkColorScheme(
    primary = Color(0xFFFFB347),
    onPrimary = Color(0xFF1A1A1A),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onBackground = Color(0xFFF5F5F5),
    onSurface = Color(0xFFF5F5F5),
    secondary = Color(0xFF4CAF50),
    tertiary = Color(0xFFEF5350)
)

@Composable
fun MesaAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ColorScheme,
        content = content
    )
}
