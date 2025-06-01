// app/src/main/java/com/example/cryptocoach/AppContentWrapper.kt
package com.example.cryptocoach

import android.app.Activity // Added
import android.content.Context // For Context.findActivity() receiver
import android.content.ContextWrapper // Added
import android.content.res.Configuration
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration // Added import
import androidx.compose.ui.platform.LocalContext
import com.example.core.SettingsRepository
import com.example.data.SettingsDataStore // Keep for DEFAULT_LANGUAGE
// kotlinx.coroutines.flow.first and kotlinx.coroutines.runBlocking can be removed
import java.util.Locale

// Helper function to find Activity from Context
fun Context.findActivity(): Activity? {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is Activity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}

// Helper composable to remember previous value
@Composable
fun <T> rememberPrevious(current: T): T? {
    val ref = remember { object { var previous: T? = null } }
    SideEffect { // Use SideEffect for operations that should happen after composition
        ref.previous = current
    }
    return ref.previous
}

@Composable
fun AppContentWrapper(
    settingsDataStore: SettingsRepository,
    content: @Composable () -> Unit
) {
    val language by settingsDataStore.getLanguagePreference()
        .collectAsState(initial = SettingsDataStore.DEFAULT_LANGUAGE) // Changed initial value

    val context = LocalContext.current
    val currentConfiguration = LocalConfiguration.current // Added
    val previousLanguage = rememberPrevious(language)

    LaunchedEffect(language) {
        if (previousLanguage != language) { // Only act if language preference string has changed
            val targetLocale = if (language.isNotBlank() && language != SettingsDataStore.DEFAULT_LANGUAGE) {
                Locale(language)
            } else {
                // Fallback to system's default locale from LocalConfiguration.current
                currentConfiguration.locales[0] ?: Locale.getDefault() // Use currentConfiguration
            }

            val currentAppLocale = currentConfiguration.locales[0] // Use currentConfiguration

            if (currentAppLocale != targetLocale) {
                val newConfig = Configuration(currentConfiguration) // Use currentConfiguration
                newConfig.setLocale(targetLocale)
                context.resources.updateConfiguration(newConfig, context.resources.displayMetrics)

                val activity = context.findActivity()
                activity?.recreate()
            }
        }
    }
    content()
}
