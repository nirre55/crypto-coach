package com.example.cryptocoach.ui.screen

import com.example.core.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow

class FakeSettingsRepository : SettingsRepository {
    val themeFlow = MutableStateFlow("System")
    val languageFlow = MutableStateFlow("System")

    override fun getThemePreference() = themeFlow
    override suspend fun saveThemePreference(theme: String) {
        themeFlow.value = theme
    }

    override fun getLanguagePreference() = languageFlow
    override suspend fun saveLanguagePreference(language: String) {
        languageFlow.value = language
    }
}

