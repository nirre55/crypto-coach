package com.example.cryptocoach.domain.usecase.settings

import com.example.cryptocoach.domain.repository.SettingsRepository // Updated import
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetThemePreferenceUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<String> {
        return settingsRepository.getThemePreference()
    }
}
