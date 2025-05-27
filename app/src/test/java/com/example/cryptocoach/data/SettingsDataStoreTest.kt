package com.example.cryptocoach.data

import android.content.Context
import android.os.Build
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.io.File

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P]) // Robolectric config for AndroidJUnit4 context
class SettingsDataStoreTest {

    private val testCoroutineDispatcher = StandardTestDispatcher()
    private val testCoroutineScope = TestScope(testCoroutineDispatcher + Job())

    private lateinit var settingsDataStore: SettingsDataStore
    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        settingsDataStore = SettingsDataStore(context)

        // Clear DataStore before each test
        testCoroutineScope.runTest {
            context.dataStore.edit { preferences ->
                preferences.clear()
            }
        }
    }

    @After
    fun tearDown() {
        // Clean up the DataStore file associated with the context.dataStore extension
        // This ensures that each test run starts with a clean slate if the clear() in setup isn't enough
        // or if tests manipulate files directly in more complex scenarios.
        val dataStoreFile = File(context.filesDir, "datastore/settings.preferences_pb")
        if (dataStoreFile.exists()) {
            dataStoreFile.deleteRecursively() // Use deleteRecursively for directories if applicable
        }
        testCoroutineScope.cancel() // Cancel the scope to avoid leaks
    }

    @Test
    fun test_saveAndGetThemePreference() = testCoroutineScope.runTest {
        settingsDataStore.saveThemePreference("Dark")
        assertEquals("Dark", settingsDataStore.getThemePreference().first())

        settingsDataStore.saveThemePreference("Light")
        assertEquals("Light", settingsDataStore.getThemePreference().first())
    }

    @Test
    fun test_getThemePreference_defaultValue() = testCoroutineScope.runTest {
        assertEquals(SettingsDataStore.DEFAULT_THEME, settingsDataStore.getThemePreference().first())
    }

    @Test
    fun test_saveAndGetLanguagePreference() = testCoroutineScope.runTest {
        settingsDataStore.saveLanguagePreference("fr")
        assertEquals("fr", settingsDataStore.getLanguagePreference().first())

        settingsDataStore.saveLanguagePreference("en")
        assertEquals("en", settingsDataStore.getLanguagePreference().first())
    }

    @Test
    fun test_getLanguagePreference_defaultValue() = testCoroutineScope.runTest {
        assertEquals(SettingsDataStore.DEFAULT_LANGUAGE, settingsDataStore.getLanguagePreference().first())
    }
}
