package com.example.cryptocoach.ui.screen

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cryptocoach.ui.screens.SettingsScreen
import com.example.cryptocoach.ui.viewmodel.SettingsViewModel
import com.example.cryptocoach.utils.TestTags
import com.example.core.LanguageOption
import com.example.core.ThemeOption
import com.example.domain.usecase.GetLanguagePreferenceUseCase
import com.example.domain.usecase.GetThemePreferenceUseCase
import com.example.domain.usecase.SaveLanguagePreferenceUseCase
import com.example.domain.usecase.SaveThemePreferenceUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class SettingsScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: SettingsViewModel

    private val getThemeUseCase       = mockk<GetThemePreferenceUseCase>()
    private val saveThemeUseCase      = mockk<SaveThemePreferenceUseCase>()
    private val getLanguageUseCase    = mockk<GetLanguagePreferenceUseCase>()
    private val saveLanguageUseCase   = mockk<SaveLanguagePreferenceUseCase>()

    // StateFlows simulant le DataStore : on démarre sur DARK / ENGLISH
    private val themeFlow    = MutableStateFlow(ThemeOption.DARK.storageValue)    // "Dark"
    private val languageFlow = MutableStateFlow(LanguageOption.ENGLISH.tag)       // "en"

    @Before
    fun setUp() {
        hiltRule.inject()

        // Mock du thème
        every { getThemeUseCase.invoke() } returns themeFlow
        coEvery { saveThemeUseCase.invoke(any()) } answers {
            val newThemeTag = it.invocation.args[0] as String
            themeFlow.value = newThemeTag
        }

        // Mock de la langue
        every { getLanguageUseCase.invoke() } returns languageFlow
        coEvery { saveLanguageUseCase.invoke(any()) } answers {
            val newLangTag = it.invocation.args[0] as String
            languageFlow.value = newLangTag
        }

        viewModel = SettingsViewModel(
            getThemeUseCase       = getThemeUseCase,
            saveThemeUseCase      = saveThemeUseCase,
            getLanguageUseCase    = getLanguageUseCase,
            saveLanguageUseCase   = saveLanguageUseCase
        )
    }

    @Test
    fun testSettingsScreenDisplaysCorrectly() {
        composeTestRule.setContent {
            SettingsScreen(viewModel = viewModel)
        }

        // Vérifier que le titre “Settings” est affiché
        composeTestRule.onNodeWithTag(TestTags.SETTINGS_TITLE)
            .assertIsDisplayed()

        // Vérifier que l'élément de préférence pour le thème est affiché
        composeTestRule.onNodeWithTag(TestTags.SETTINGS_THEME_ITEM)
            .assertIsDisplayed()

        // Vérifier que l'élément de préférence pour la langue est affiché
        composeTestRule.onNodeWithTag(TestTags.SETTINGS_LANGUAGE_ITEM)
            .assertIsDisplayed()
    }

    @Test
    fun testThemeDialogOpensAndCloses() {
        composeTestRule.setContent {
            SettingsScreen(viewModel = viewModel)
        }

        // Ouvrir le dialogue du thème
        composeTestRule.onNodeWithTag(TestTags.SETTINGS_THEME_ITEM)
            .performClick()

        // L'option “Light” doit apparaître
        composeTestRule.onNodeWithTag("${TestTags.THEME_DIALOG}_option_Light")
            .assertIsDisplayed()

        // Fermer le dialogue en cliquant sur “Light”
        composeTestRule.onNodeWithTag("${TestTags.THEME_DIALOG}_option_Light")
            .performClick()

        // Vérifier que l'option “Light” n'est plus dans le DOM
        composeTestRule.onNodeWithTag("${TestTags.THEME_DIALOG}_option_Light")
            .assertDoesNotExist()
    }

    @Test
    fun testLanguageDialogOpensAndCloses() {
        composeTestRule.setContent {
            SettingsScreen(viewModel = viewModel)
        }

        // Ouvrir le dialogue de langue
        composeTestRule.onNodeWithTag(TestTags.SETTINGS_LANGUAGE_ITEM)
            .performClick()

        // L'option “en” (English) doit apparaître
        composeTestRule.onNodeWithTag("${TestTags.LANGUAGE_DIALOG}_option_en")
            .assertIsDisplayed()

        // Fermer le dialogue en cliquant sur “en”
        composeTestRule.onNodeWithTag("${TestTags.LANGUAGE_DIALOG}_option_en")
            .performClick()

        // Vérifier que l'option “en” n'est plus dans le DOM
        composeTestRule.onNodeWithTag("${TestTags.LANGUAGE_DIALOG}_option_en")
            .assertDoesNotExist()
    }

    @Test
    fun testSelectThemeOption() {
        composeTestRule.setContent {
            SettingsScreen(viewModel = viewModel)
        }

        // 1) Avant : le thème par défaut est “Dark”
        composeTestRule.onNodeWithTag(TestTags.SETTINGS_THEME_ITEM)
            .assertTextContains("Dark")

        // 2) Ouvrir le dialogue
        composeTestRule.onNodeWithTag(TestTags.SETTINGS_THEME_ITEM)
            .performClick()

        // 3) Cliquer sur l'option “Light”
        composeTestRule.onNodeWithTag("${TestTags.THEME_DIALOG}_option_Light")
            .performClick()

        // 4) Attendre la mise à jour
        composeTestRule.waitForIdle()

        // 5) Vérifier que l'item affiche maintenant “Light”
        composeTestRule.onNodeWithTag(TestTags.SETTINGS_THEME_ITEM)
            .assertTextContains("Light")
    }

    @Test
    fun testSelectLanguageOption() {
        composeTestRule.setContent {
            SettingsScreen(viewModel = viewModel)
        }

        // 1) Avant : la langue par défaut est “English”
        composeTestRule.onNodeWithTag(TestTags.SETTINGS_LANGUAGE_ITEM)
            .assertTextContains("English")

        // 2) Ouvrir le dialogue
        composeTestRule.onNodeWithTag(TestTags.SETTINGS_LANGUAGE_ITEM)
            .performClick()

        // 3) Cliquer sur l'option “fr” (French)
        composeTestRule.onNodeWithTag("${TestTags.LANGUAGE_DIALOG}_option_fr")
            .performClick()

        // 4) Attendre la mise à jour
        composeTestRule.waitForIdle()

        // 5) Vérifier que l'item affiche maintenant “French”
        composeTestRule.onNodeWithTag(TestTags.SETTINGS_LANGUAGE_ITEM)
            .assertTextContains("French")
    }
}
