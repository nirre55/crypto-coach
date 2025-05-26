package com.example.cryptocoach.navigation
import androidx.annotation.StringRes
import com.example.cryptocoach.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, @StringRes val titleRes: Int, val icon: ImageVector) {
    data object Dashboard : Screen("dashboard", R.string.dashboard, Icons.Default.Home)
    data object Education : Screen("education", R.string.education, Icons.Default.School)
    data object Patterns : Screen("patterns", R.string.patterns, Icons.Default.BarChart)
    data object Simulator : Screen("simulator", R.string.simulator, Icons.Default.PlayArrow)
    data object Settings : Screen("settings", R.string.settings, Icons.Default.Settings)
    data object About : Screen("about", R.string.about, Icons.Default.Info)
}
