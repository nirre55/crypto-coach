package com.example.cryptocoach.ui.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.cryptocoach.ui.screen.FakeSettingsRepository
import com.example.cryptocoach.utils.TestTags
import org.junit.Rule
import org.junit.Test

class SettingsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val fakeRepo = FakeSettingsRepository()

    @Test
    fun settingsTitle_isVisible() {
        composeTestRule.setContent {
            SettingsScreen(settingsRepo = fakeRepo)
        }

        composeTestRule.onNodeWithTag(TestTags.SETTINGS_TITLE).assertExists()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun clickingTheme_showsDialog_andSelectsDark() {
        composeTestRule.setContent {
            SettingsScreen(settingsRepo = fakeRepo)
        }

        composeTestRule.onNodeWithTag(TestTags.SETTINGS_THEME_ITEM).performClick()

        // Attendre que l’option "Dark" apparaisse
        composeTestRule.waitUntilExactlyOneExists(
            hasTestTag("${TestTags.THEME_DIALOG}_option_Dark"),
            timeoutMillis = 5_000
        )

        composeTestRule.onNodeWithTag("${TestTags.THEME_DIALOG}_option_Dark").performClick()

        assert(fakeRepo.getThemePreference().value == "Dark")
    }

    @Test
    fun clickingLanguage_showsDialog_andSelectsFrench() {
        composeTestRule.setContent {
            SettingsScreen(settingsRepo = fakeRepo)
        }

        composeTestRule.onNodeWithTag(TestTags.SETTINGS_LANGUAGE_ITEM).performClick()
        composeTestRule.onNodeWithTag("${TestTags.LANGUAGE_DIALOG}_option_fr").performClick()

        assert(fakeRepo.getLanguagePreference().value == "fr")
    }
}
