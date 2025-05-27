package com.example.cryptocoach.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Define Context.dataStore extension
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsDataStore(private val context: Context) {

    companion object {
        val THEME_KEY = stringPreferencesKey("theme_preference")
        val LANGUAGE_KEY = stringPreferencesKey("language_preference")
        const val DEFAULT_THEME = "System"
        const val DEFAULT_LANGUAGE = "System"
    }

    fun getThemePreference(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: DEFAULT_THEME
        }
    }

    suspend fun saveThemePreference(theme: String) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = theme
        }
    }

    fun getLanguagePreference(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[LANGUAGE_KEY] ?: DEFAULT_LANGUAGE
        }
    }

    suspend fun saveLanguagePreference(language: String) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language
        }
    }
}
