package com.example.cryptocoach.domain.usecase.settings

import com.example.cryptocoach.domain.repository.SettingsRepository // Updated import
import javax.inject.Inject

class SaveThemePreferenceUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(theme: String) {
        settingsRepository.saveThemePreference(theme)
    }
}
