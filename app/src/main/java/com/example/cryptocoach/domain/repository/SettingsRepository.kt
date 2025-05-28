package com.example.cryptocoach.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getThemePreference(): Flow<String>
    suspend fun saveThemePreference(theme: String)

    fun getLanguagePreference(): Flow<String>
    suspend fun saveLanguagePreference(language: String)
}
