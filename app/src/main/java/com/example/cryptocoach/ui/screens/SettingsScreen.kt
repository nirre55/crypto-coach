package com.example.cryptocoach.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cryptocoach.R
import com.example.cryptocoach.ui.components.PreferenceItem
import com.example.cryptocoach.ui.components.SelectionAlertDialog
import com.example.cryptocoach.ui.viewmodel.SettingsViewModel
import com.example.cryptocoach.utils.TestTags

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    // On récupère directement le StateFlow exposé par le ViewModel
    val currentTheme by viewModel.themeState.collectAsState()
    val currentLanguage by viewModel.languageState.collectAsState()

    var showThemeDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }

    val themeOptions = listOf(
        Pair(stringResource(R.string.theme_light), "Light"),
        Pair(stringResource(R.string.theme_dark), "Dark"),
        Pair(stringResource(R.string.theme_system), "System")
    )

    val languageOptions = listOf(
        Pair(stringResource(R.string.language_system), "System"),
        Pair(stringResource(R.string.language_en), "en"),
        Pair(stringResource(R.string.language_fr), "fr")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
            .testTag(TestTags.SETTINGS_SCREEN)
    ) {
        Text(
            text = stringResource(R.string.settings),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .testTag(TestTags.SETTINGS_TITLE)
        )

        PreferenceItem(
            title = stringResource(R.string.theme),
            currentValue = themeOptions.firstOrNull { it.second == currentTheme }?.first
                ?: currentTheme,
            onClick = { showThemeDialog = true },
            testTag = TestTags.SETTINGS_THEME_ITEM
        )
        HorizontalDivider()

        PreferenceItem(
            title = stringResource(R.string.language),
            currentValue = languageOptions.firstOrNull { it.second == currentLanguage }?.first
                ?: currentLanguage,
            onClick = { showLanguageDialog = true },
            testTag = TestTags.SETTINGS_LANGUAGE_ITEM
        )
        HorizontalDivider()
    }

    // Boîte de dialogue pour le choix de thème
    SelectionAlertDialog(
        showDialog = showThemeDialog,
        onDismissRequest = { showThemeDialog = false },
        title = stringResource(R.string.theme_select_title),
        options = themeOptions,
        currentValue = currentTheme,
        onOptionSelected = { viewModel.onThemeSelected(it) },
        testTagPrefix = TestTags.THEME_DIALOG
    )

    // Boîte de dialogue pour le choix de langue
    SelectionAlertDialog(
        showDialog = showLanguageDialog,
        onDismissRequest = { showLanguageDialog = false },
        title = stringResource(R.string.language_select_title),
        options = languageOptions,
        currentValue = currentLanguage,
        onOptionSelected = { viewModel.onLanguageSelected(it) },
        testTagPrefix = TestTags.LANGUAGE_DIALOG
    )
}
