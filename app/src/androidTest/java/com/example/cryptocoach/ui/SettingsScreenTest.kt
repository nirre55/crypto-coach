package com.example.cryptocoach.ui

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.cryptocoach.R
import com.example.cryptocoach.data.SettingsDataStore
import com.example.cryptocoach.ui.screens.SettingsScreen
import com.example.cryptocoach.ui.theme.CryptoCoachTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

// It's generally better to use the actual datastore name used by the app for integration tests,
// or inject the datastore name/instance if possible.
// Since SettingsDataStore uses "settings" by default, we will clear that.
// The testDataStore extension is not strictly necessary here as we'll use the app's default.

@RunWith(AndroidJUnit4::class)
class SettingsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var context: Context
    private lateinit var settingsDataStore: SettingsDataStore

    // Helper to get the datastore file used by SettingsDataStore
    private fun getDataStoreFile(context: Context): File {
        return File(context.filesDir, "datastore/${SettingsDataStore.DEFAULT_SETTINGS_FILE_NAME}.preferences_pb")
    }


    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        settingsDataStore = SettingsDataStore(context)

        // Clean up DataStore before each test to ensure test independence
        runBlocking {
            // Clear the specific DataStore file used by the app
            val dataStoreFile = getDataStoreFile(context)
            if (dataStoreFile.exists()) {
                // settingsDataStore.dataStore.edit { it.clear() } would be better if we had direct access to the DataStore instance.
                // Since SettingsDataStore encapsulates it via context.dataStore, direct deletion is an alternative
                // if context.dataStore.edit { it.clear() } doesn't work as expected across test runs
                // or if we want to be absolutely sure.
                // However, the most idiomatic way is to clear preferences:
                context.dataStore.edit { preferences ->
                    preferences.clear()
                }
            }
            // Pre-populate with System defaults for most tests
            settingsDataStore.saveThemePreference(SettingsDataStore.DEFAULT_THEME)
            settingsDataStore.saveLanguagePreference(SettingsDataStore.DEFAULT_LANGUAGE)
        }
    }

    @After
    fun tearDown() {
        // Clean up DataStore after each test
        runBlocking {
            context.dataStore.edit { preferences ->
                preferences.clear()
            }
            // As an extra measure, or if clearing preferences is not enough due to caching or other issues:
            // val dataStoreFile = getDataStoreFile(context)
            // if (dataStoreFile.exists()) {
            //     dataStoreFile.delete()
            // }
        }
    }

    private fun getString(id: Int) = context.getString(id)

    @Test
    fun testThemePreference_DialogOpensAndOptionClickable() {
        composeTestRule.setContent {
            CryptoCoachTheme(themePreferenceString = SettingsDataStore.DEFAULT_THEME) {
                SettingsScreen(settingsDataStore = settingsDataStore)
            }
        }

        // Click on Theme preference
        composeTestRule.onNodeWithText(getString(R.string.theme)).performClick()

        // Check if dialog with title "Select Theme" is displayed
        composeTestRule.onNodeWithText(getString(R.string.theme_select_title)).assertIsDisplayed()

        // Click on "Light" option
        composeTestRule.onNodeWithText(getString(R.string.theme_light)).performClick()

        // Check if dialog is dismissed
        composeTestRule.onNodeWithText(getString(R.string.theme_select_title)).assertDoesNotExist()
        
        runBlocking {
            assertEquals("Light", settingsDataStore.getThemePreference().first())
        }
    }

    @Test
    fun testLanguagePreference_DialogOpensAndOptionClickable() {
        composeTestRule.setContent {
            CryptoCoachTheme(themePreferenceString = SettingsDataStore.DEFAULT_THEME) {
                SettingsScreen(settingsDataStore = settingsDataStore)
            }
        }

        // Click on Language preference
        composeTestRule.onNodeWithText(getString(R.string.language)).performClick()

        // Check if dialog with title "Select Language" is displayed
        composeTestRule.onNodeWithText(getString(R.string.language_select_title)).assertIsDisplayed()

        // Click on "English" option (value is "en")
        composeTestRule.onNodeWithText(getString(R.string.language_en)).performClick()

        // Check if dialog is dismissed
        composeTestRule.onNodeWithText(getString(R.string.language_select_title)).assertDoesNotExist()

        runBlocking {
            assertEquals("en", settingsDataStore.getLanguagePreference().first())
        }
    }

    @Test
    fun testInitialDisplayOfPreferences() {
        // Set specific initial values
        runBlocking {
            settingsDataStore.saveThemePreference("Dark")
            settingsDataStore.saveLanguagePreference("fr")
        }

        composeTestRule.setContent {
            // CryptoCoachTheme needs to reflect the "Dark" theme for this test to be fully accurate
            // if the display names depend on the current theme (which they don't in this case, but good practice).
            CryptoCoachTheme(themePreferenceString = "Dark") { 
                SettingsScreen(settingsDataStore = settingsDataStore)
            }
        }

        // Check for "Dark" (the display name for the "Dark" value)
        // We are checking the text of the preference item, which should be the display name
        composeTestRule.onNodeWithText(getString(R.string.theme_dark)).assertIsDisplayed()


        // Check for "Français" (the display name for the "fr" value)
        composeTestRule.onNodeWithText(getString(R.string.language_fr)).assertIsDisplayed()
    }
}
