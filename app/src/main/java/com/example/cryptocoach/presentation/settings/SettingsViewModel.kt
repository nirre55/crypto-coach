package com.example.cryptocoach.presentation.settings // Updated package

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocoach.data.repository.local.SettingsRepositoryImpl // Updated import for default values
import com.example.cryptocoach.domain.usecase.settings.GetLanguagePreferenceUseCase
import com.example.cryptocoach.domain.usecase.settings.GetThemePreferenceUseCase
import com.example.cryptocoach.domain.usecase.settings.SaveLanguagePreferenceUseCase
import com.example.cryptocoach.domain.usecase.settings.SaveThemePreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject
import android.content.Context
import android.content.res.Configuration

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getThemePreferenceUseCase: GetThemePreferenceUseCase,
    private val saveThemePreferenceUseCase: SaveThemePreferenceUseCase,
    private val getLanguagePreferenceUseCase: GetLanguagePreferenceUseCase,
    private val saveLanguagePreferenceUseCase: SaveLanguagePreferenceUseCase
) : ViewModel() {

    val themePreference: StateFlow<String> = getThemePreferenceUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SettingsRepositoryImpl.DEFAULT_THEME // Use new path for default
        )

    val languagePreference: StateFlow<String> = getLanguagePreferenceUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SettingsRepositoryImpl.DEFAULT_LANGUAGE // Use new path for default
        )

    fun saveThemePreference(theme: String) {
        viewModelScope.launch {
            saveThemePreferenceUseCase(theme)
        }
    }

    fun saveLanguagePreference(language: String) {
        viewModelScope.launch {
            saveLanguagePreferenceUseCase(language)
        }
    }

    fun applyLocale(context: Context) {
        val currentLanguage = if (languagePreference.value.isNotBlank()) languagePreference.value else SettingsRepositoryImpl.DEFAULT_LANGUAGE
        val locale = if (currentLanguage != SettingsRepositoryImpl.DEFAULT_LANGUAGE) {
            Locale(currentLanguage)
        } else {
            context.resources.configuration.locales[0]
        }

        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}
