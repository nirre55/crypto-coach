package com.example.domain.usecase

import com.example.core.SettingsRepository
import javax.inject.Inject


class SaveLanguagePreferenceUseCase @Inject constructor(
    private val repo: SettingsRepository
) {
    suspend operator fun invoke(language: String) =
        repo.saveLanguagePreference(language)
}