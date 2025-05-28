package com.example.cryptocoach.presentation // Updated package

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cryptocoach.R // R import is fine
import com.example.cryptocoach.data.repository.local.SettingsRepositoryImpl // Updated for default values
import com.example.cryptocoach.presentation.navigation.AppNavGraph // Correct
import com.example.cryptocoach.presentation.navigation.Screen // Correct
import com.example.cryptocoach.presentation.settings.SettingsViewModel // Correct
import com.example.cryptocoach.presentation.theme.CryptoCoachTheme // Correct
import com.example.cryptocoach.presentation.components.DrawerItem // Correct
import com.example.cryptocoach.utils.TestTags // Assuming TestTags.kt was not moved
import java.util.Locale

// Composable to handle locale changes
@Composable
fun AppContentWrapper(
    viewModel: SettingsViewModel,
    content: @Composable () -> Unit
) {
    val language by viewModel.languagePreference.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(language) {
        viewModel.applyLocale(context)
    }

    val updatedContext = remember(language) {
        val currentLocaleToApply = if (language.isNotBlank() && language != SettingsRepositoryImpl.DEFAULT_LANGUAGE) {
            Locale(language)
        } else {
            context.resources.configuration.locales[0]
        }
        val config = Configuration(context.resources.configuration)
        config.setLocale(currentLocaleToApply)
        context.createConfigurationContext(config)
    }

    CompositionLocalProvider(LocalContext provides updatedContext) {
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoCoachApp(viewModel: SettingsViewModel = hiltViewModel()) {
    val themePreference by viewModel.themePreference.collectAsStateWithLifecycle()

    CryptoCoachTheme(themePreferenceString = themePreference) {
        AppContentWrapper(viewModel = viewModel) {
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
                            title = { Text(stringResource(R.string.app_name)) },
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
                        AppNavGraph(navController)
                    }
                }
            }
        }
    }
}
