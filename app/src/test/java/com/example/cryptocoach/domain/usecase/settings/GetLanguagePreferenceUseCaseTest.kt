package com.example.cryptocoach.domain.usecase.settings

import app.cash.turbine.test
import com.example.cryptocoach.domain.repository.SettingsRepository // Updated import
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetLanguagePreferenceUseCaseTest {

    private val mockSettingsRepository: SettingsRepository = mock()
    private val useCase = GetLanguagePreferenceUseCase(mockSettingsRepository)

    @Test
    fun `invoke should return language preference from repository`() = runTest {
        val expectedLang = "en"
        whenever(mockSettingsRepository.getLanguagePreference()).thenReturn(flowOf(expectedLang))

        useCase.invoke().test {
            assertEquals(expectedLang, awaitItem())
            awaitComplete()
        }
    }
}
