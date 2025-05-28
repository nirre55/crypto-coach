package com.example.cryptocoach.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
// Updated import for the dataStore extension
import com.example.cryptocoach.data.local.dataStore
// Updated import for the SettingsRepositoryImpl
import com.example.cryptocoach.data.repository.local.SettingsRepositoryImpl
// Updated import for the SettingsRepository interface
import com.example.cryptocoach.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSettingsRepository(@ApplicationContext context: Context): SettingsRepository {
        // Ensure SettingsRepositoryImpl is instantiated and context.dataStore is correctly referenced
        return SettingsRepositoryImpl(context.dataStore)
    }
}
