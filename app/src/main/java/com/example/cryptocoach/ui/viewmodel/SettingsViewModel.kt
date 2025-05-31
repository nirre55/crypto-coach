package com.example.cryptocoach.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getTheme: GetThemePreferenceUseCase,
    private val saveTheme: SaveThemePreferenceUseCase,
    private val getLanguage: GetLanguagePreferenceUseCase,
    private val saveLanguage: SaveLanguagePreferenceUseCase
) : ViewModel() {

    val themeState: StateFlow<String> = getTheme()
        .stateIn(viewModelScope, SharingStarted.Eagerly, "System")

    val languageState: StateFlow<String> = getLanguage()
        .stateIn(viewModelScope, SharingStarted.Eagerly, "System")

    fun onThemeSelected(theme: String) = viewModelScope.launch {
        saveTheme(theme)
    }

    fun onLanguageSelected(lang: String) = viewModelScope.launch {
        saveLanguage(lang)
    }
}
