package com.example.cryptocoach.navigation

import androidx.navigation.NavHostController

fun navigateSafeTo(navController: NavHostController, route: String) {
    if (navController.currentDestination?.route == route) return // évite re-navigation inutile

    navController.navigate(route) {
        popUpTo(navController.graph.startDestinationId) {
            inclusive = false
            saveState = false
        }
        launchSingleTop = true
        restoreState = true
    }
}
