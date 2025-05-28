package com.example.cryptocoach

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.* // Required for collectAsState and rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext // Required for context
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cryptocoach.data.SettingsDataStore // Added
import com.example.cryptocoach.navigation.AppNavGraph
import com.example.cryptocoach.navigation.Screen
import com.example.cryptocoach.ui.theme.CryptoCoachTheme
import com.example.cryptocoach.ui.components.DrawerItem
import com.example.cryptocoach.utils.TestTags
import kotlinx.coroutines.flow.first // For initial language load
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking // For initial language load
import java.util.Locale // Required for Locale

// Composable to handle locale changes
@Composable
fun AppContentWrapper(
    settingsDataStore: SettingsDataStore,
    content: @Composable () -> Unit
) {
    val language by settingsDataStore.getLanguagePreference().collectAsState(initial = runBlocking { settingsDataStore.getLanguagePreference().first() })
    val context = LocalContext.current

    LaunchedEffect(language) {
        if (language != SettingsDataStore.DEFAULT_LANGUAGE) {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val config = Configuration(context.resources.configuration)
            config.setLocale(locale)
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        } else {
            // Use system language - reset to default if previously changed
            val systemLocale = Locale.getDefault() // Or get system default more reliably
            val config = Configuration(context.resources.configuration)
            config.setLocale(systemLocale) // This might need to be the original system locale
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        }
    }
    // Update context for the composable tree
    val updatedContext = remember(language) {
        val currentLocale = if (language == SettingsDataStore.DEFAULT_LANGUAGE) {
            context.resources.configuration.locales[0] // More reliable way to get current/system default
        } else {
            Locale(language)
        }
        val config = Configuration(context.resources.configuration)
        config.setLocale(currentLocale)
        context.createConfigurationContext(config)
    }

    CompositionLocalProvider(LocalContext provides updatedContext) {
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoCoachApp(settingsDataStore: SettingsDataStore) { // Modified parameter
    // Collect theme preference
    val themePreference by settingsDataStore.getThemePreference().collectAsState(initial = SettingsDataStore.DEFAULT_THEME)

    // Pass themePreference to CryptoCoachTheme
    CryptoCoachTheme(themePreferenceString = themePreference) {
        // Wrap the main content with AppContentWrapper for language changes
        AppContentWrapper(settingsDataStore = settingsDataStore) {
            val navController = rememberNavController()
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val menuItems = listOf(
                Screen.Dashboard,
                Screen.Education,
                Screen.Patterns,
                Screen.Simulator
            )

            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        Spacer(Modifier.height(16.dp))
                        LazyColumn(modifier = Modifier.weight(1f)) {
                            items(menuItems) { screen ->
                                DrawerItem(
                                    screen = screen,
                                    currentRoute = currentRoute,
                                    onClick = {
                                        scope.launch { drawerState.close() }
                                        navController.navigate(screen.route) {
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    testTag = "drawer_${screen.route}"
                                )
                            }
                        }
                        HorizontalDivider()
                        DrawerItem(
                            screen = Screen.Settings,
                            currentRoute = currentRoute,
                            onClick = {
                                scope.launch { drawerState.close() }
                                // Pass settingsDataStore to SettingsScreen if it needs direct access,
                                // or handle through a ViewModel. For now, ensure it's available.
                                navController.navigate(Screen.Settings.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            testTag = TestTags.DRAWER_SETTINGS
                        )
                        DrawerItem(
                            screen = Screen.About,
                            currentRoute = currentRoute,
                            onClick = {
                                scope.launch { drawerState.close() }
                                navController.navigate(Screen.About.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            testTag = TestTags.DRAWER_ABOUT
                        )
                    }
                }
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(stringResource(R.string.app_name)) }, // Use stringResource for app name
                            navigationIcon = {
                                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                    Icon(
                                        Icons.Default.Menu,
                                        contentDescription = stringResource(R.string.nav_menu)
                                    )
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        AppNavGraph(navController, settingsDataStore) // Pass settingsDataStore
                    }
                }
            }
        }
    }
}
