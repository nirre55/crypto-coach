// app/src/main/java/com/example/cryptocoach/AppContentWrapper.kt
package com.example.cryptocoach  // ou com.example.cryptocoach.ui si vous avez choisi un sous-package

import android.content.res.Configuration
import androidx.compose.runtime.*
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import com.example.data.SettingsDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.Locale

/**
 * Composable responsable de gérer le changement de locale au lancement
 * ainsi que de fournir un contexte « mise à jour » via CompositionLocalProvider.
 */
@Composable
fun AppContentWrapper(
    settingsDataStore: SettingsDataStore,
    content: @Composable () -> Unit
) {
    // On collecte en Flow la préférence de langue
    val language by settingsDataStore.getLanguagePreference()
        .collectAsState(initial = runBlocking { settingsDataStore.getLanguagePreference().first() })

    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    // Dès que la langue change, on met à jour la configuration de l’application
    LaunchedEffect(language) {
        val localeToApply = if (language != SettingsDataStore.DEFAULT_LANGUAGE) {
            Locale(language)
        } else {
            Locale.getDefault()
        }
        val newConfig = Configuration(configuration).apply {
            setLocale(localeToApply)
        }
        context.resources.updateConfiguration(newConfig, context.resources.displayMetrics)
    }

    // On crée un nouveau Context configuré avec la locale choisie 
    val updatedContext = remember(language, configuration) {
        val chosenLocale = if (language == SettingsDataStore.DEFAULT_LANGUAGE) {
            configuration.locales[0]
        } else {
            Locale(language)
        }
        val newConfig = Configuration(configuration).apply {
            setLocale(chosenLocale)
        }
        context.createConfigurationContext(newConfig)
    }

    // Lorsqu’on descend dans l’arborescence Compose, on fournit ce Context localement
    CompositionLocalProvider(LocalContext provides updatedContext) {
        content()
    }
}
