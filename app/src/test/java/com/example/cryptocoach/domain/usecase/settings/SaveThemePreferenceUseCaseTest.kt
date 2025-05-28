package com.example.cryptocoach.domain.usecase.settings

import com.example.cryptocoach.domain.repository.SettingsRepository // Updated import
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.coVerify // For suspend functions

class SaveThemePreferenceUseCaseTest {

    private val mockSettingsRepository: SettingsRepository = mock()
    private val useCase = SaveThemePreferenceUseCase(mockSettingsRepository)

    @Test
    fun `invoke should call saveThemePreference on repository`() = runTest {
        val themeToSave = "Light"
        useCase.invoke(themeToSave)
        coVerify { mockSettingsRepository.saveThemePreference(themeToSave) }
    }
}
