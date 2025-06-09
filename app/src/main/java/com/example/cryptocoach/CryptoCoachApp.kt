package com.example.cryptocoach

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cryptocoach.navigation.AppNavGraph
import com.example.cryptocoach.navigation.Screen
import com.example.cryptocoach.navigation.navigateSafeTo
import com.example.cryptocoach.ui.components.DrawerItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoCoachApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val mainDrawerScreens = listOf(
        Screen.Dashboard,
        Screen.Education,
        Screen.Patterns,
        Screen.Simulator
    )

    val secondaryDrawerScreens = listOf(
        Screen.Settings,
        Screen.About
    )

    val allScreens = mainDrawerScreens + secondaryDrawerScreens

    val title = currentRoute?.let { route ->
        allScreens.find { screen -> route.startsWith(screen.route) }?.titleRes
    } ?: R.string.app_name

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(16.dp))
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(mainDrawerScreens) { screen ->
                        DrawerItem(
                            screen = screen,
                            currentRoute = currentRoute,
                            onClick = {
                                scope.launch { drawerState.close() }
                                navigateSafeTo(navController, screen.route)
                            },
                            testTag = "drawer_${screen.route}"
                        )
                    }
                }
                HorizontalDivider()
                secondaryDrawerScreens.forEach { screen ->
                    DrawerItem(
                        screen = screen,
                        currentRoute = currentRoute,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navigateSafeTo(navController, screen.route)
                        },
                        testTag = "drawer_${screen.route}"
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = title)) },
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
