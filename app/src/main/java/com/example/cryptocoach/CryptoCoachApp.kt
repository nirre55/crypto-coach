package com.example.cryptocoach
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.cryptocoach.navigation.AppNavGraph
import com.example.cryptocoach.navigation.Screen
import com.example.cryptocoach.utils.TestTags
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoCoachApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Éléments scrollable (principal)
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
                        NavigationDrawerItem(
                            icon = { Icon(screen.icon, contentDescription = null) },
                            label = { Text(stringResource(screen.titleRes)) },
                            selected = false,
                            onClick = {
                                scope.launch { drawerState.close() }
                                navController.navigate(screen.route)
                            },
                            modifier = Modifier.testTag("drawer_${screen.route}")
                        )
                    }
                }
                HorizontalDivider()
                NavigationDrawerItem(
                    icon = { Icon(Screen.Settings.icon, contentDescription = null) },
                    label = { Text(stringResource(Screen.Settings.titleRes)) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screen.Settings.route)
                    },
                    modifier = Modifier.testTag(TestTags.DRAWER_SETTINGS)
                )

                NavigationDrawerItem(
                    icon = { Icon(Screen.About.icon, contentDescription = null) },
                    label = { Text(stringResource(Screen.About.titleRes)) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(Screen.About.route)
                    },
                    modifier = Modifier.testTag(TestTags.DRAWER_ABOUT)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("CryptoCoach") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = stringResource(R.string.nav_menu))
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
