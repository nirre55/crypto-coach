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
    val currentTheme by viewModel.themeOption.collectAsState()
    val currentLanguage by viewModel.languageOption.collectAsState()

    var showThemeDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }

    var selectedThemeOption by remember(currentTheme) { mutableStateOf(currentTheme) }
    var selectedLanguageOption by remember(currentLanguage) { mutableStateOf(currentLanguage) }

    val themeOptions = listOf(
        stringResource(R.string.theme_light) to ThemeOption.LIGHT,
        stringResource(R.string.theme_dark) to ThemeOption.DARK,
        stringResource(R.string.theme_system) to ThemeOption.SYSTEM
    )

    val languageOptions = listOf(
        stringResource(R.string.language_system) to LanguageOption.SYSTEM,
        stringResource(R.string.language_en) to LanguageOption.ENGLISH,
        stringResource(R.string.language_fr) to LanguageOption.FRENCH
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .testTag(TestTags.SETTINGS_SCREEN)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.preferences),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(12.dp))

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

    // LaunchedEffects
    LaunchedEffect(selectedThemeOption) {
        if (selectedThemeOption != currentTheme) {
            showThemeDialog = false
            viewModel.onThemeSelected(selectedThemeOption)
        }
    }

    LaunchedEffect(selectedLanguageOption) {
        if (selectedLanguageOption != currentLanguage) {
            showLanguageDialog = false
            viewModel.onLanguageSelected(selectedLanguageOption)
        }
    }

    if (showThemeDialog) {
        SelectionAlertDialog(
            showDialog = showThemeDialog,
            onDismissRequest = { showThemeDialog = false },
            title = stringResource(R.string.theme_select_title),
            options = themeOptions.map { it.first to it.second.storageValue },
            currentValue = currentTheme.storageValue,
            onOptionSelected = {
                selectedThemeOption = ThemeOption.fromStorage(it)
            },
            testTagPrefix = TestTags.THEME_DIALOG
        )
    }

    if (showLanguageDialog) {
        SelectionAlertDialog(
            showDialog = showLanguageDialog,
            onDismissRequest = { showLanguageDialog = false },
            title = stringResource(R.string.language_select_title),
            options = languageOptions.map { it.first to it.second.tag },
            currentValue = currentLanguage.tag,
            onOptionSelected = {
                selectedLanguageOption = LanguageOption.fromStorage(it)
            },
            testTagPrefix = TestTags.LANGUAGE_DIALOG
        )
    }
}
