// DataStoreExtensions.kt
package com.example.cryptocoach.data.local // Updated package

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: androidx.datastore.core.DataStore<Preferences> by preferencesDataStore(name = "settings")
