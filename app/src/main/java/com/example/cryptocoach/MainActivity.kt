package com.example.cryptocoach

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.core.SettingsRepository // Added import
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject // Import Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject // Inject SettingsDataStore
    lateinit var settingsDataStore: SettingsRepository // Type changed here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Pass settingsDataStore to CryptoCoachApp
            CryptoCoachApp(settingsDataStore = settingsDataStore)
        }
    }
}