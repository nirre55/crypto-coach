package com.example.cryptocoach.presentation.navigation // Updated package

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
// Import screens from their new presentation.screens path
import com.example.cryptocoach.presentation.screens.DashboardScreen
import com.example.cryptocoach.presentation.screens.EducationScreen
import com.example.cryptocoach.presentation.screens.PatternScreen
import com.example.cryptocoach.presentation.screens.SimulatorScreen
import com.example.cryptocoach.presentation.settings.SettingsScreen // SettingsScreen is already in presentation.settings
import com.example.cryptocoach.presentation.screens.AboutScreen
import com.example.cryptocoach.presentation.navigation.Screen // Added import for Screen sealed class

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
