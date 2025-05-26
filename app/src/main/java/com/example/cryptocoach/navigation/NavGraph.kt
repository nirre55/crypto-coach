package com.example.cryptocoach.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cryptocoach.ui.screens.*

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Dashboard.route) {
        composable(Screen.Dashboard.route) { DashboardScreen() }
        composable(Screen.Education.route) { EducationScreen() }
        composable(Screen.Patterns.route) { PatternScreen() }
        composable(Screen.Simulator.route) { SimulatorScreen() }
        composable(Screen.Settings.route) { SettingsScreen() }
        composable(Screen.About.route) { AboutScreen() }
    }
}
