package com.example.domain.usecase

import com.example.core.SettingsRepository
import javax.inject.Inject

class SaveThemePreferenceUseCase @Inject constructor(
    private val repo: SettingsRepository
) {
    suspend operator fun invoke(theme: String) =
        repo.saveThemePreference(theme)
}