package com.example.cryptocoach

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.core.SettingsRepository // Ensure this import is present
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cryptocoach.navigation.AppNavGraph
import com.example.cryptocoach.navigation.Screen
import com.example.cryptocoach.ui.components.DrawerItem
import com.example.cryptocoach.ui.theme.CryptoCoachTheme
import com.example.cryptocoach.utils.TestTags
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoCoachApp(settingsDataStore: SettingsRepository) { // Parameter type changed here
    // Collect theme preference
    val themePreferenceString by settingsDataStore.getThemePreference()
        .collectAsState(initial = "System") // Default to "System"

    // NavController + Drawer
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Les items principaux du Drawer
    val menuItems = listOf(
        Screen.Dashboard,
        Screen.Education,
        Screen.Patterns,
        Screen.Simulator
    )

    CryptoCoachTheme(themePreferenceString = themePreferenceString) {
        // AppContentWrapper now wraps the part of the UI that needs language updates
        AppContentWrapper(settingsDataStore = settingsDataStore) {
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
