package com.example.cryptocoach.domain.usecase.settings

import com.example.cryptocoach.domain.repository.SettingsRepository // Updated import
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.coVerify

class SaveLanguagePreferenceUseCaseTest {

    private val mockSettingsRepository: SettingsRepository = mock()
    private val useCase = SaveLanguagePreferenceUseCase(mockSettingsRepository)

    @Test
    fun `invoke should call saveLanguagePreference on repository`() = runTest {
        val langToSave = "fr"
        useCase.invoke(langToSave)
        coVerify { mockSettingsRepository.saveLanguagePreference(langToSave) }
    }
}
