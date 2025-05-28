package com.example.cryptocoach.domain.usecase.settings

import com.example.cryptocoach.domain.repository.SettingsRepository // Updated import
import javax.inject.Inject

class SaveLanguagePreferenceUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(language: String) {
        settingsRepository.saveLanguagePreference(language)
    }
}
