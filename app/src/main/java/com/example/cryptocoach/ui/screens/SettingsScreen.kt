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
import com.example.core.ThemeOption
import com.example.core.LanguageOption

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    // Observer les StateFlow exposés par le ViewModel
    val currentTheme by viewModel.themeOption.collectAsState()
    val currentLanguage by viewModel.languageOption.collectAsState()

    var showThemeDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }

    // Liste des options de thème (affichage texte + valeur enum)
    val themeOptions = listOf(
        stringResource(R.string.theme_light) to ThemeOption.LIGHT,
        stringResource(R.string.theme_dark) to ThemeOption.DARK,
        stringResource(R.string.theme_system) to ThemeOption.SYSTEM
    )
    // Liste des options de langue (affichage texte + valeur enum)
    val languageOptions = listOf(
        stringResource(R.string.language_system) to LanguageOption.SYSTEM,
        stringResource(R.string.language_en) to LanguageOption.ENGLISH,
        stringResource(R.string.language_fr) to LanguageOption.FRENCH
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
            currentValue = themeOptions.first { it.second == currentTheme }.first,
            onClick = { showThemeDialog = true },
            testTag = TestTags.SETTINGS_THEME_ITEM
        )
        HorizontalDivider()

        PreferenceItem(
            title = stringResource(R.string.language),
            currentValue = languageOptions.first { it.second == currentLanguage }.first,
            onClick = { showLanguageDialog = true },
            testTag = TestTags.SETTINGS_LANGUAGE_ITEM
        )
        HorizontalDivider()
    }

    // Boîte de dialogue pour choisir le thème
    if (showThemeDialog) {
        SelectionAlertDialog(
            showDialog = showThemeDialog,
            onDismissRequest = { showThemeDialog = false },
            title = stringResource(R.string.theme_select_title),
            // Conversion en Pair<Affichage, String> où String = storageValue (ThemeOption.storageValue)
            options = themeOptions.map { it.first to it.second.storageValue },
            currentValue = currentTheme.storageValue,
            onOptionSelected = { selectedValue ->
                val option = ThemeOption.fromStorage(selectedValue)
                viewModel.onThemeSelected(option)
                showThemeDialog = false
            },
            testTagPrefix = TestTags.THEME_DIALOG
        )
    }

    // Boîte de dialogue pour choisir la langue
    if (showLanguageDialog) {
        SelectionAlertDialog(
            showDialog = showLanguageDialog,
            onDismissRequest = { showLanguageDialog = false },
            title = stringResource(R.string.language_select_title),
            // Conversion en Pair<Affichage, String> où String = tag (LanguageOption.tag)
            options = languageOptions.map { it.first to it.second.tag },
            currentValue = currentLanguage.tag,
            onOptionSelected = { selectedValue ->
                val option = LanguageOption.fromStorage(selectedValue)
                viewModel.onLanguageSelected(option)
                showLanguageDialog = false
            },
            testTagPrefix = TestTags.LANGUAGE_DIALOG
        )
    }
}
