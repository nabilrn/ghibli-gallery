package com.example.comsposesubmission.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Define the new primary color
val NewPrimaryColor = Color(0xFF2596BE)



// Update the LightColorScheme with the new primary color
private val LightColorScheme = lightColorScheme(
    primary = NewPrimaryColor, // Updated primary color
    secondary = PurpleGrey40,
    tertiary = Pink40
)

// Update the DarkColorScheme with the new primary color
private val DarkColorScheme = darkColorScheme(
    primary = NewPrimaryColor, // Updated primary color
    secondary = PurpleGrey80,
    tertiary = Pink80
)

@Composable
fun ComsposeSubmissionTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}