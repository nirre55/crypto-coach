package com.example.cryptocoach.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.UUID
import java.nio.file.Path
import kotlin.io.path.createTempDirectory

class SettingsDataStoreTest {

    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var settingsDataStore: SettingsDataStore
    private lateinit var tempDir: Path

    @Before
    fun setup() {
        tempDir = createTempDirectory("test_settings_${UUID.randomUUID()}")
        val testFile = tempDir.resolve("settings.preferences_pb").toFile()

        dataStore = PreferenceDataStoreFactory.create(
            produceFile = { testFile }
        )

        settingsDataStore = SettingsDataStore(dataStore)
    }

    @After
    fun tearDown() {
        tempDir.toFile().deleteRecursively()
    }

    @Test
    fun defaultTheme_isSystem() = runTest {
        val theme = settingsDataStore.getThemePreference().first()
        assertEquals(SettingsDataStore.DEFAULT_THEME, theme)
    }

    @Test
    fun saveThemePreference_updatesValue() = runTest {
        settingsDataStore.saveThemePreference("Dark")
        val theme = settingsDataStore.getThemePreference().first()
        assertEquals("Dark", theme)
    }

    @Test
    fun defaultLanguage_isSystem() = runTest {
        val language = settingsDataStore.getLanguagePreference().first()
        assertEquals(SettingsDataStore.DEFAULT_LANGUAGE, language)
    }

    @Test
    fun saveLanguagePreference_updatesValue() = runTest {
        settingsDataStore.saveLanguagePreference("fr")
        val language = settingsDataStore.getLanguagePreference().first()
        assertEquals("fr", language)
    }
}
