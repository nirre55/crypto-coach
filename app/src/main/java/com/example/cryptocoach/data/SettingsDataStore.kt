package com.example.cryptocoach.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map



class SettingsDataStore(private val dataStore: DataStore<Preferences>) : SettingsRepository{

    companion object {
        val THEME_KEY = stringPreferencesKey("theme_preference")
        val LANGUAGE_KEY = stringPreferencesKey("language_preference")
        const val DEFAULT_THEME = "System"
        const val DEFAULT_LANGUAGE = "System"
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
