package com.example.cryptocoach.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.LanguageOption
import com.example.core.model.ThemeOption
import com.example.domain.usecase.GetLanguagePreferenceUseCase
import com.example.domain.usecase.GetThemePreferenceUseCase
import com.example.domain.usecase.SaveLanguagePreferenceUseCase
import com.example.domain.usecase.SaveThemePreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.core.os.LocaleListCompat
import androidx.appcompat.app.AppCompatDelegate
import javax.inject.Inject

/**
 * ViewModel dédié aux paramètres (thème + langue).
 * Il convertit les String de DataStore en ThemeOption / LanguageOption,
 * expose ces valeurs sous forme de StateFlow, et applique le changement
 * de locale via AppCompatDelegate.setApplicationLocales(...).
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    getThemeUseCase: GetThemePreferenceUseCase,
    private val saveThemeUseCase: SaveThemePreferenceUseCase,
    getLanguageUseCase: GetLanguagePreferenceUseCase,
    private val saveLanguageUseCase: SaveLanguagePreferenceUseCase
) : ViewModel() {

    // StateFlow exposant l’option de thème actuelle
    private val _themeOption: StateFlow<ThemeOption> = getThemeUseCase()
        .map { ThemeOption.fromStorage(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = ThemeOption.SYSTEM
        )
    val themeOption: StateFlow<ThemeOption> = _themeOption

    // StateFlow exposant l’option de langue actuelle
    private val _languageOption: StateFlow<LanguageOption> = getLanguageUseCase()
        .map { LanguageOption.fromStorage(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = LanguageOption.SYSTEM
        )
    val languageOption: StateFlow<LanguageOption> = _languageOption

    /** Appelé quand l’utilisateur sélectionne un nouveau thème dans l’UI */
    fun onThemeSelected(option: ThemeOption) = viewModelScope.launch {
        saveThemeUseCase(option.storageValue)
        // (Optionnel) Vous pourriez aussi appliquer directement AppCompatDelegate.setDefaultNightMode(...)
        // mais ici, le thème est appliqué via Compose (isSystemInDarkTheme() + MaterialTheme).
    }

    /** Appelé quand l’utilisateur sélectionne une nouvelle langue dans l’UI */
    fun onLanguageSelected(option: LanguageOption) = viewModelScope.launch {
        saveLanguageUseCase(option.tag)
        // Applique la locale de l’application immédiatement, sans redémarrage complet.
        val localeList = when (option) {
            LanguageOption.SYSTEM -> LocaleListCompat.getEmptyLocaleList()
            else -> LocaleListCompat.forLanguageTags(option.tag)
        }
        AppCompatDelegate.setApplicationLocales(localeList)
    }
}
