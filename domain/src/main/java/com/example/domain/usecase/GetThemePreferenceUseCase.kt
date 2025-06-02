package com.example.domain.usecase

import com.example.core.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetThemePreferenceUseCase @Inject constructor(
    private val repo: SettingsRepository
) {
    operator fun invoke(): Flow<String> =
        repo.getThemePreference()
}
