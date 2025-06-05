package com.example.cryptocoach

import android.text.TextUtils
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.os.ConfigurationCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cryptocoach.ui.theme.CryptoCoachTheme
import com.example.cryptocoach.ui.viewmodel.SettingsViewModel

/**
 * Composable racine :
 * 1) Observe les StateFlow (themeOption, languageOption) dans SettingsViewModel.
 * 2) Détermine si on doit appliquer le mode sombre ou non.
 * 3) Récupère LocalConfiguration.current pour forcer recomposition sur changement de locale.
 * 4) Calcule la LayoutDirection (RTL/LTR) en fonction de la locale actuelle.
 * 5) Applique CryptoCoachTheme(...) autour de AppNavGraph(navController).
 */

@Composable
fun AppRootContent() {
    // Récupération du ViewModel injecté par Hilt
    val viewModel: SettingsViewModel = hiltViewModel()

    // On observe l’option de thème et l’option de langue
    val themeOption by viewModel.themeOption.collectAsState()
    //val languageOption by viewModel.languageOption.collectAsState()

    // On “observe” la configuration actuelle du device (utile pour détecter les changements de locale)
    val configuration = LocalConfiguration.current

    // Détermination du mode sombre ou clair
    /*val useDarkTheme: Boolean = when (themeOption) {
        com.example.core.ThemeOption.DARK -> true
        com.example.core.ThemeOption.LIGHT -> false
        com.example.core.ThemeOption.SYSTEM -> isSystemInDarkTheme()
    }*/

    // Récupérer la locale actuelle (premier élément du LocaleList)
    val currentLocale = ConfigurationCompat.getLocales(configuration)[0]

    // Calculer la LayoutDirection Compose en fonction de la locale (RTL si nécessaire)
    val layoutDirection = if (TextUtils.getLayoutDirectionFromLocale(currentLocale) == View.LAYOUT_DIRECTION_RTL) {
        LayoutDirection.Rtl
    } else {
        LayoutDirection.Ltr
    }

    // Fournir manuellement la LayoutDirection aux enfants Compose
    androidx.compose.runtime.CompositionLocalProvider(
        LocalLayoutDirection provides layoutDirection
    ) {
        CryptoCoachTheme(themeOption = themeOption) {
            // Démarrage de la navigation Compose
            CryptoCoachApp()
        }
    }
}