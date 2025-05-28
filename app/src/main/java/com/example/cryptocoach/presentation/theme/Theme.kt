package com.example.cryptocoach.presentation.theme // Updated package

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Assuming color definitions (PrimaryDark, etc.) are in the same 'presentation.theme' package
// If not, explicit imports for them would be needed.

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    secondary = SecondaryDark,
    tertiary = TertiaryDark,
    background = BackgroundDark,
    surface = SurfaceDark,
    onPrimary = OnPrimaryDark,
    onSecondary = OnSecondaryDark,
    onTertiary = OnTertiaryDark,
    onBackground = OnBackgroundDark,
    onSurface = OnSurfaceDark
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    secondary = SecondaryLight,
    tertiary = TertiaryLight,
    background = BackgroundLight,
    surface = SurfaceLight,
    onPrimary = OnPrimaryLight,
    onSecondary = OnSecondaryLight,
    onTertiary = OnTertiaryLight,
    onBackground = OnBackgroundLight,
    onSurface = OnSurfaceLight
)

@Composable
fun CryptoCoachTheme(
    themePreferenceString: String,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val actualDarkTheme: Boolean = when (themePreferenceString) {
        "Light" -> false
        "Dark" -> true
        else -> isSystemInDarkTheme()
    }

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (actualDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        actualDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Assuming Typography is in the same 'presentation.theme' package
        content = content
    )
}
