package com.example.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.core.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implémentation de SettingsRepository via DataStore (Preferences).
 * Stocke deux clés : THEME_KEY et LANGUAGE_KEY, avec des strings.
 */
class SettingsDataStore(private val dataStore: DataStore<Preferences>) : SettingsRepository {

    companion object {
        val THEME_KEY = stringPreferencesKey("theme_preference")
        val LANGUAGE_KEY = stringPreferencesKey("language_preference")
        const val DEFAULT_THEME = "System"
        const val DEFAULT_LANGUAGE = "system"
    }

    override fun getThemePreference(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: DEFAULT_THEME
        }
    }

    override suspend fun saveThemePreference(theme: String) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = theme
        }
    }

    override fun getLanguagePreference(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[LANGUAGE_KEY] ?: DEFAULT_LANGUAGE
        }
    }

    override suspend fun saveLanguagePreference(language: String) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language
        }
    }
}
