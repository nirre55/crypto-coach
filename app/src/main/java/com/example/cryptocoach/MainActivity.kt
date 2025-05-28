package com.example.cryptocoach

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.cryptocoach.presentation.CryptoCoachApp // Updated import
import dagger.hilt.android.AndroidEntryPoint

// SettingsRepository import and field removed
// import com.example.cryptocoach.data.SettingsRepository
// import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // @Inject // Remove
    // lateinit var settingsRepository: SettingsRepository // Remove

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // CryptoCoachApp will get its own ViewModel or use one provided at a higher Composable scope
            CryptoCoachApp() // settingsRepository parameter removed
        }
    }
}