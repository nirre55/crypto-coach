package com.example.cryptocoach.domain.usecase.settings

import app.cash.turbine.test
import com.example.cryptocoach.domain.repository.SettingsRepository // Updated import
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetThemePreferenceUseCaseTest {

    private val mockSettingsRepository: SettingsRepository = mock()
    private val useCase = GetThemePreferenceUseCase(mockSettingsRepository)

    @Test
    fun `invoke should return theme preference from repository`() = runTest {
        val expectedTheme = "Dark"
        whenever(mockSettingsRepository.getThemePreference()).thenReturn(flowOf(expectedTheme))

        useCase.invoke().test {
            assertEquals(expectedTheme, awaitItem())
            awaitComplete()
        }
    }
}
