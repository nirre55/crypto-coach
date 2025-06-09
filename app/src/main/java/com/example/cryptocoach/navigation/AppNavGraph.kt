package com.example.cryptocoach.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cryptocoach.model.PatternGroup
import com.example.cryptocoach.repository.DefaultPatternRepository
import com.example.cryptocoach.ui.screens.*

@Composable
fun AppNavGraph(navController: NavHostController) {
    // ✅ Crée une instance unique du repository
    val repository = DefaultPatternRepository()

    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        // 🔹 Écrans fixes
        composable(Screen.Dashboard.route) {
            DashboardScreen()
        }
        composable(Screen.Education.route) {
            EducationScreen()
        }
        composable(Screen.Patterns.route) {
            PatternScreen(navController)
        }
        composable(Screen.Simulator.route) {
            SimulatorScreen()
        }
        composable(Screen.Settings.route) {
            SettingsScreen()
        }
        composable(Screen.About.route) {
            AboutScreen()
        }

        // 🔹 Écran liste dynamique : patternList/{type}
        composable("patternList/{group}") { backStackEntry ->
            val group = backStackEntry.arguments?.getString("group")?.let {
                runCatching { PatternGroup.valueOf(it) }.getOrNull()
            }

            if (group != null) {
                PatternListScreen(
                    group = group,
                    repository = repository,
                    onPatternClick = { id ->
                        navController.navigate("patternDetail/$id")
                    }
                )
            }
        }

        // 🔹 Écran détail : patternDetail/{id}
        composable("patternDetail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            if (id != null) {
                PatternDetailScreen(
                    patternId = id,
                    repository = repository
                )
            }
        }
    }
}