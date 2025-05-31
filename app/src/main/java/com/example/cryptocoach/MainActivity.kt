package com.example.cryptocoach

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.data.SettingsDataStore
import com.example.data.dataStore // ✅ Import manquant


class MainActivity : ComponentActivity() {
    private lateinit var settingsDataStore: SettingsDataStore // Added

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsDataStore = SettingsDataStore(applicationContext.dataStore) // Added
        enableEdgeToEdge()
        setContent {
            CryptoCoachApp(settingsDataStore) // Modified
        }
    }
}