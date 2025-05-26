package com.example.cryptocoach.navigation

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.cryptocoach.MainActivity
import com.example.cryptocoach.utils.TestTags
import org.junit.Test
import org.junit.Rule

class NavGraphTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun startDestination_isDashboard() {
        // Le texte de l'écran Dashboard est "Dashboard"
        composeTestRule.onNodeWithTag(TestTags.DASHBOARD_TITLE).assertExists()
    }

    @Test
    fun clickingDashboard_navigatesToDashboardScreen() {
        navigateAndAssertVisible(TestTags.DRAWER_DASHBOARD, TestTags.DASHBOARD_TITLE)
    }

    @Test
    fun clickingEducation_navigatesToEducationScreen() {
        navigateAndAssertVisible(TestTags.DRAWER_EDUCATION, TestTags.EDUCATION_TITLE)
    }

    @Test
    fun clickingSimulator_navigatesToSimulatorScreen() {
        navigateAndAssertVisible(TestTags.DRAWER_SIMULATOR, TestTags.SIMULATOR_TITLE)
    }

    @Test
    fun clickingPatterns_navigatesToPatternScreen() {
        navigateAndAssertVisible(TestTags.DRAWER_PATTERNS, TestTags.PATTERN_TITLE)
    }

    @Test
    fun clickingSettings_navigatesToSettingsScreen() {
        navigateAndAssertVisible(TestTags.DRAWER_SETTINGS, TestTags.SETTINGS_TITLE)
    }

    @Test
    fun clickingAbout_navigatesToAboutScreen() {
        navigateAndAssertVisible(TestTags.DRAWER_ABOUT, TestTags.ABOUT_TITLE)
    }

    private fun navigateAndAssertVisible(drawerTag: String, screenTag: String) {
        // Ouvrir le menu
        composeTestRule.onNodeWithContentDescription("Menu").performClick()
        // Cliquer sur l’entrée drawertag ex:Education, Simulation ...
        composeTestRule.onNodeWithTag(drawerTag).performClick()
        // Vérifier qu’on est bien sur le bon écran
        composeTestRule.onNodeWithTag(screenTag).assertExists()
    }
}