package com.example.comsposesubmission.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = TwilightBlue,
    onPrimary = Color.White,
    primaryContainer = TwilightBlue.copy(alpha = 0.1f),
    onPrimaryContainer = TwilightBlue,
    secondary = Terracotta,
    onSecondary = Color.White,
    secondaryContainer = Terracotta.copy(alpha = 0.12f),
    onSecondaryContainer = Terracotta,
    tertiary = Sage,
    onTertiary = Color.White,
    tertiaryContainer = Sage.copy(alpha = 0.12f),
    onTertiaryContainer = Sage,
    background = PaperCream,
    onBackground = WarmCharcoal,
    surface = WarmWhite,
    onSurface = WarmCharcoal,
    surfaceVariant = Parchment,
    onSurfaceVariant = SoftCharcoal,
    outline = Color(0xFFC4B8A4),
    outlineVariant = Color(0xFFDDD5C8),
    surfaceContainerHighest = Parchment,
    surfaceContainerHigh = Color(0xFFF5EDE2),
    surfaceContainer = Color(0xFFF8F2EA),
    surfaceContainerLow = WarmWhite,
)

private val DarkColorScheme = darkColorScheme(
    primary = MutedSky,
    onPrimary = Color(0xFF0D2137),
    primaryContainer = TwilightBlue,
    onPrimaryContainer = MutedSky,
    secondary = WarmAmber,
    onSecondary = Color(0xFF332200),
    secondaryContainer = Color(0xFF5C3F1A),
    onSecondaryContainer = WarmAmber,
    tertiary = SoftSage,
    onTertiary = Color(0xFF0D2615),
    background = NightSky,
    onBackground = CreamText,
    surface = DeepNavy,
    onSurface = CreamText,
    surfaceVariant = Color(0xFF2A3040),
    onSurfaceVariant = Color(0xFFB0A898),
    outline = Color(0xFF4A5568),
    outlineVariant = Color(0xFF343D4D),
    surfaceContainerHighest = ElevatedNavy,
    surfaceContainerHigh = Color(0xFF212838),
    surfaceContainer = Color(0xFF1E2535),
    surfaceContainerLow = Color(0xFF181E2B),
)

@Composable
fun ComsposeSubmissionTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
