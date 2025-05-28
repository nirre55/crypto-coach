package com.example.cryptocoach.presentation.settings // Updated package

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import app.cash.turbine.test
// Updated import for default values (now from SettingsRepositoryImpl)
import com.example.cryptocoach.data.repository.local.SettingsRepositoryImpl
import com.example.cryptocoach.domain.usecase.settings.GetLanguagePreferenceUseCase
import com.example.cryptocoach.domain.usecase.settings.GetThemePreferenceUseCase
import com.example.cryptocoach.domain.usecase.settings.SaveLanguagePreferenceUseCase
import com.example.cryptocoach.domain.usecase.settings.SaveThemePreferenceUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import java.util.Locale

@ExperimentalCoroutinesApi
class SettingsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getThemePreferenceUseCase: GetThemePreferenceUseCase
    private lateinit var saveThemePreferenceUseCase: SaveThemePreferenceUseCase
    private lateinit var getLanguagePreferenceUseCase: GetLanguagePreferenceUseCase
    private lateinit var saveLanguagePreferenceUseCase: SaveLanguagePreferenceUseCase
    private lateinit var viewModel: SettingsViewModel // Will use the presentation.settings.SettingsViewModel

    // MutableStateFlow to control the language preference for applyLocale tests
    private val currentLanguageFlow = MutableStateFlow(SettingsRepositoryImpl.DEFAULT_LANGUAGE)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getThemePreferenceUseCase = mock()
        saveThemePreferenceUseCase = mock()
        getLanguagePreferenceUseCase = mock()
        saveLanguagePreferenceUseCase = mock()

        whenever(getThemePreferenceUseCase.invoke()).thenReturn(flowOf(SettingsRepositoryImpl.DEFAULT_THEME))
        // Link the mock GetLanguagePreferenceUseCase to the MutableStateFlow
        whenever(getLanguagePreferenceUseCase.invoke()).thenReturn(currentLanguageFlow)

        viewModel = SettingsViewModel( // This should now instantiate the presentation.settings.SettingsViewModel
            getThemePreferenceUseCase,
            saveThemePreferenceUseCase,
            getLanguagePreferenceUseCase,
            saveLanguagePreferenceUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `themePreference initializes with default theme from use case`() = runTest {
        val expectedDefaultTheme = SettingsRepositoryImpl.DEFAULT_THEME
        viewModel.themePreference.test {
            assertEquals(expectedDefaultTheme, awaitItem())
        }
    }

    @Test
    fun `languagePreference initializes with default language from use case`() = runTest {
        val expectedDefaultLang = SettingsRepositoryImpl.DEFAULT_LANGUAGE
        currentLanguageFlow.value = SettingsRepositoryImpl.DEFAULT_LANGUAGE // Ensure flow emits default
        viewModel.languagePreference.test {
            assertEquals(expectedDefaultLang, awaitItem())
        }
    }

    @Test
    fun `saveThemePreference calls use case`() = runTest {
        val theme = "Dark"
        viewModel.saveThemePreference(theme)
        testDispatcher.scheduler.advanceUntilIdle()
        coVerify { saveThemePreferenceUseCase(theme) }
    }

    @Test
    fun `saveLanguagePreference calls use case`() = runTest {
        val lang = "fr"
        viewModel.saveLanguagePreference(lang)
        testDispatcher.scheduler.advanceUntilIdle()
        coVerify { saveLanguagePreferenceUseCase(lang) }
    }

    @Test
    fun `applyLocale uses provided language from languagePreference and updates context`() = runTest {
        val mockContext: Context = mock()
        val mockResources: Resources = mock()
        val mockConfiguration: Configuration = mock()

        whenever(mockContext.resources).thenReturn(mockResources)
        whenever(mockResources.configuration).thenReturn(mockConfiguration)
        whenever(mockConfiguration.locales).thenReturn(mock()) // Avoid NPE on locales[0]
        whenever(mockConfiguration.locales.get(0)).thenReturn(Locale.ENGLISH) // Default system locale

        val targetLanguage = "fr"
        currentLanguageFlow.value = targetLanguage // Set the language preference
        testDispatcher.scheduler.advanceUntilIdle() // Allow StateFlow to update

        viewModel.applyLocale(mockContext)

        verify(mockResources).updateConfiguration(argThat { it.locale == Locale(targetLanguage) }, eq(mockContext.displayMetrics))
    }

    @Test
    fun `applyLocale uses system default if languagePreference is DEFAULT_LANGUAGE`() = runTest {
        val mockContext: Context = mock()
        val mockResources: Resources = mock()
        val mockConfiguration: Configuration = mock()
        val systemLocale = Locale.GERMAN

        whenever(mockContext.resources).thenReturn(mockResources)
        whenever(mockResources.configuration).thenReturn(mockConfiguration)
        whenever(mockConfiguration.locales).thenReturn(mock())
        whenever(mockConfiguration.locales.get(0)).thenReturn(systemLocale)

        currentLanguageFlow.value = SettingsRepositoryImpl.DEFAULT_LANGUAGE
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.applyLocale(mockContext)
        verify(mockResources).updateConfiguration(argThat { it.locale == systemLocale }, eq(mockContext.displayMetrics))
    }

    @Test
    fun `applyLocale uses system default if languagePreference is blank`() = runTest {
        val mockContext: Context = mock()
        val mockResources: Resources = mock()
        val mockConfiguration: Configuration = mock()
        val systemLocale = Locale.ITALIAN

        whenever(mockContext.resources).thenReturn(mockResources)
        whenever(mockResources.configuration).thenReturn(mockConfiguration)
        whenever(mockConfiguration.locales).thenReturn(mock())
        whenever(mockConfiguration.locales.get(0)).thenReturn(systemLocale)

        currentLanguageFlow.value = "" // Set language to blank
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.applyLocale(mockContext)
        // It should use the systemLocale because languagePreference.value is blank
        verify(mockResources).updateConfiguration(argThat { it.locale == systemLocale }, eq(mockContext.displayMetrics))
    }
}
