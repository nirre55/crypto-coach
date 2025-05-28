package com.example.cryptocoach.data.repository.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.cryptocoach.domain.repository.SettingsRepository // Updated import
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton // Added for Hilt
class SettingsRepositoryImpl @Inject constructor( // Added @Inject constructor
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    companion object {
        val THEME_KEY = stringPreferencesKey("theme_preference")
        val LANGUAGE_KEY = stringPreferencesKey("language_preference")
        // These defaults might be better placed in the domain or as part of UseCase logic
        // if they represent business rules, but for now, they remain here.
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
