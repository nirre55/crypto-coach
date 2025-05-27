package com.example.cryptocoach.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cryptocoach.R
import com.example.cryptocoach.data.SettingsDataStore
import com.example.cryptocoach.utils.TestTags
import kotlinx.coroutines.launch

// Simple preference item composable
@Composable
fun PreferenceItem(title: String, currentValue: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        Text(text = currentValue, style = MaterialTheme.typography.bodyMedium)
    }
}

// AlertDialog for selecting an option
@Composable
fun SelectionAlertDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    title: String,
    options: List<Pair<String, String>>, // Pair of (display name, value to save)
    currentValue: String,
    onOptionSelected: (String) -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(title) },
            text = {
                Column {
                    options.forEach { (displayName, value) ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onOptionSelected(value)
                                    onDismissRequest()
                                }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (value == currentValue),
                                onClick = {
                                    onOptionSelected(value)
                                    onDismissRequest()
                                }
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(displayName)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(stringResource(android.R.string.cancel))
                }
            }
        )
    }
}


@Composable
fun SettingsScreen(settingsDataStore: SettingsDataStore) {
    val scope = rememberCoroutineScope()

    // Theme preference
    val currentTheme by settingsDataStore.getThemePreference().collectAsState(initial = SettingsDataStore.DEFAULT_THEME)
    var showThemeDialog by remember { mutableStateOf(false) }
    val themeOptions = listOf(
        Pair(stringResource(R.string.theme_light), "Light"),
        Pair(stringResource(R.string.theme_dark), "Dark"),
        Pair(stringResource(R.string.theme_system), "System")
    )

    // Language preference
    val currentLanguage by settingsDataStore.getLanguagePreference().collectAsState(initial = SettingsDataStore.DEFAULT_LANGUAGE)
    var showLanguageDialog by remember { mutableStateOf(false) }
    // Assuming "System", "English (en)", "French (fr)"
    // Values should be language codes like "en", "fr"
    val languageOptions = listOf(
        Pair(stringResource(R.string.language_system), "System"),
        Pair(stringResource(R.string.language_en), "en"),
        Pair(stringResource(R.string.language_fr), "fr")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
            .testTag(TestTags.SETTINGS_TITLE) // Keep existing test tag if it's for the whole screen
    ) {
        Text(
            text = stringResource(R.string.settings),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        PreferenceItem(
            title = stringResource(R.string.theme),
            currentValue = themeOptions.firstOrNull { it.second == currentTheme }?.first ?: currentTheme,
            onClick = { showThemeDialog = true }
        )
        HorizontalDivider()

        PreferenceItem(
            title = stringResource(R.string.language),
            currentValue = languageOptions.firstOrNull { it.second == currentLanguage }?.first ?: currentLanguage,
            onClick = { showLanguageDialog = true }
        )
        HorizontalDivider()
    }

    SelectionAlertDialog(
        showDialog = showThemeDialog,
        onDismissRequest = { showThemeDialog = false },
        title = stringResource(R.string.theme_select_title),
        options = themeOptions,
        currentValue = currentTheme,
        onOptionSelected = { theme ->
            scope.launch {
                settingsDataStore.saveThemePreference(theme)
            }
        }
    )

    SelectionAlertDialog(
        showDialog = showLanguageDialog,
        onDismissRequest = { showLanguageDialog = false },
        title = stringResource(R.string.language_select_title),
        options = languageOptions,
        currentValue = currentLanguage,
        onOptionSelected = { language ->
            scope.launch {
                settingsDataStore.saveLanguagePreference(language)
            }
        }
    )
}