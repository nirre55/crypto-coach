package com.example.cryptocoach

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity configurée pour gérer à la volée :
 *  - Le thème (clair/sombre/système) via Compose + isSystemInDarkTheme()
 *  - La langue (anglais/français/système) via AppCompatDelegate.setApplicationLocales(...)
 *  - empêchant le redémarrage grâce à configChanges="locale|layoutDirection|uiMode"
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // On applique le theme AppCompat nécessaire à AppCompatDelegate.setApplicationLocales(...)
        setTheme(R.style.Theme_CryptoCoach)
        super.onCreate(savedInstanceState)
        setContent {
            AppRootContent()
        }
    }
}

